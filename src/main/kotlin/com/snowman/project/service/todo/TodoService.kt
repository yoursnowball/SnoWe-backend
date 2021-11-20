package com.snowman.project.service.todo

import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
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
    fun getTodos(user: User, goal: Goal, date: LocalDate): List<TodoInfoDto> {
        val startDateTime = date.atTime(0, 0, 0, 0)
        val endDateTime = date.atTime(23, 59, 59, 59)

        if (goal.user != user)
            throw NotYourContentException()
        return todoRepository.findAllByGoalAndCreatedAtBetween(goal, startDateTime, endDateTime).map { TodoInfoDto(it) }
    }

    fun saveTodos(userId: Long, goalId: Long, todos: List<String>, date: LocalDate): List<TodoInfoDto> {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted || goal.awarded)
            throw CannotAddTodoException()

        todoRepository.saveAll(todos.map { Todo(goal = goal, user = user, name = it, todoDate = date) })

        return getTodos(user, goal, LocalDate.now())
    }

    fun updateToDo(userId: Long, goalId: Long, todoId: Long, name: String, succeed: Boolean): TodoInfoDto {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException()

        if (goal.user != user || todo.goal != goal)
            throw NotYourContentException()

        if (!todo.canUpdateOrDelete())
            throw CannotEditTodoException()

        if (todo.update(name, succeed)) {
            val isLevelUp = goal.todoSucceed()
            if (isLevelUp)
                pushService.sendPushMessage(user, PushType.LEVELUP, SimpleGoalInfoDto(goal))

            if (todoRepository.countAllByGoalAndTodoDateAndSucceedIsFalse(goal, LocalDate.now()) == 0)
                pushService.sendPushMessage(user, PushType.ALLCLEAR, SimpleGoalInfoDto(goal))
        }

        return TodoInfoDto(todo)
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