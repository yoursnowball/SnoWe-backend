package com.snowman.project.service.todo

import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.dao.todo.projections.TodoWIthGoalIdDto
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.LevelChange
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.model.todo.dto.TodoInfoDto
import com.snowman.project.model.todo.entity.Todo
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.goal.exceptions.GoalNotExistException
import com.snowman.project.service.push.PushService
import com.snowman.project.service.todo.exceptions.CannotAddTodoException
import com.snowman.project.service.todo.exceptions.CannotDeleteSucceedTodoException
import com.snowman.project.service.todo.exceptions.CannotEditTodoException
import com.snowman.project.service.todo.exceptions.TodoNotExistException
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class TodoService(
    private val todoRepository: TodoRepository,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val pushService: PushService
) {

    @Transactional(readOnly = true)
    fun getTodosByDate(user: User, date: LocalDate): Map<Long, List<TodoWIthGoalIdDto>> {
        return todoRepository.getTodosWithGoalByUserAndDate(user, date).groupBy { it.goalId }
    }

    @Transactional(readOnly = true)
    fun getTodosByGoalAndDate(user: User, goal: Goal, date: LocalDate): List<TodoInfoDto> {

        if (goal.user != user)
            throw NotYourContentException()
        return todoRepository.findAllByGoalAndTodoDate(goal, date).map { TodoInfoDto(it) }
    }

    fun saveTodos(userId: Long, goalId: Long, todo: String, date: LocalDate): List<TodoInfoDto> {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted || goal.awarded)
            throw CannotAddTodoException()

        todoRepository.save(Todo(goal = goal, user = user, name = todo, todoDate = date))

        return getTodosByGoalAndDate(user, goal, date)
    }

    fun updateToDo(
        userId: Long,
        goalId: Long,
        todoId: Long,
        name: String,
        succeed: Boolean
    ): Pair<TodoInfoDto, LevelChange> {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException()
        var isLevelChange = LevelChange.KEEP

        if (goal.user != user || todo.goal != goal)
            throw NotYourContentException()

        if (!todo.canUpdateOrDelete())
            throw CannotEditTodoException()

        if (todo.update(name, succeed)) {
            isLevelChange = goal.todoChange(succeed)
            if (isLevelChange == LevelChange.LEVELUP)
                pushService.saveAlarmMessage(user, PushType.LEVELUP, SimpleGoalInfoDto(goal))
            if (todoRepository.countAllByGoalAndTodoDateAndSucceedIsFalse(goal, LocalDate.now()) == 0)
                pushService.saveAlarmMessage(user, PushType.ALLCLEAR, SimpleGoalInfoDto(goal))
        }

        return Pair(TodoInfoDto(todo), isLevelChange)
    }

    fun deleteTodo(userId: Long, goalId: Long, todoId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException()

        if (goal.user != user || todo.goal != goal)
            throw NotYourContentException()

        if (!todo.canUpdateOrDelete())
            throw CannotEditTodoException()

        if (todo.succeed)
            throw CannotDeleteSucceedTodoException()

        todoRepository.delete(todo)
    }
}