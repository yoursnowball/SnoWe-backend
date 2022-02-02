package com.snowman.project.todos.service

import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.domain.enums.LevelChange
import com.snowman.project.goals.service.exceptions.GoalNotExistException
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.todos.domain.dto.TodoInfoDto
import com.snowman.project.todos.domain.entity.Todo
import com.snowman.project.todos.service.exceptions.CannotAddTodoException
import com.snowman.project.todos.service.exceptions.CannotDeleteSucceedTodoException
import com.snowman.project.todos.service.exceptions.CannotEditTodoException
import com.snowman.project.todos.service.exceptions.TodoNotExistException
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import com.snowman.project.users.service.exceptions.UserNotExistException
import org.springframework.context.ApplicationEventPublisher
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
    private val publisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getTodosByGoalAndDate(user: User, goal: Goal, date: LocalDate): List<TodoInfoDto> {
        if (goal.user.id != user.id)
            throw NotYourContentException()
        return todoRepository.findAllByGoalAndTodoDate(goal, date).map { TodoInfoDto(it) }
    }

    fun saveTodos(userId: Long, goalId: Long, todo: String, date: LocalDate): List<TodoInfoDto> {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()

        if (goal.user.id != user.id)
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
        val prevLevel = goal.level

        if (goal.user.id != user.id || todo.goal.id != goal.id)
            throw NotYourContentException()
        if (!todo.canUpdateOrDelete())
            throw CannotEditTodoException()
        todo.update(name, succeed)
        todo.pollAllEvent().forEach { publisher.publishEvent(it) }
        return Pair(TodoInfoDto(todo), checkLevelChange(prevLevel, goal.level))
    }

    fun deleteTodo(userId: Long, goalId: Long, todoId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException()

        if (goal.user.id != user.id || todo.goal.id != goal.id)
            throw NotYourContentException()

        if (!todo.canUpdateOrDelete())
            throw CannotEditTodoException()

        if (todo.succeed)
            throw CannotDeleteSucceedTodoException()

        todoRepository.delete(todo)
    }

    private fun checkLevelChange(prev: Int, now: Int): LevelChange {
        return if (prev == now)
            LevelChange.KEEP
        else if (prev > now)
            LevelChange.LEVELDOWN
        else
            LevelChange.LEVELUP
    }
}