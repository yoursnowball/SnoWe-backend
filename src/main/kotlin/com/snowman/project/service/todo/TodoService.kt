package com.snowman.project.service.todo

import com.snowman.project.config.exceptions.ErrorCode
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.todo.dto.TodoInfoDto
import com.snowman.project.model.todo.entity.Todo
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.goal.exceptions.GoalNotExistException
import com.snowman.project.service.todo.exceptions.CannotDeleteSucceedTodoException
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
        private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getTodos(user: User, goal: Goal, date: LocalDate): List<TodoInfoDto> {
        val startDateTime = date.atTime(0, 0, 0, 0)
        val endDateTime = date.atTime(23, 59, 59, 59)

        if (goal.user != user)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)
        return todoRepository.findAllByGoalAndCreateAtBetween(goal, startDateTime, endDateTime).map { TodoInfoDto(it) }
    }

    fun saveTodos(userId: Long, goalId: Long, todos: List<String>): List<TodoInfoDto> {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException(ErrorCode.GOAL_NOT_EXIST)
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)

        if (goal.user != user)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)
        todoRepository.saveAll(todos.map { Todo(goal = goal, name = it) })

        return getTodos(user, goal, LocalDate.now())
    }

    fun updateToDo(userId: Long, goalId: Long, todoId: Long, name: String, succeed: Boolean): TodoInfoDto {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException(ErrorCode.GOAL_NOT_EXIST)
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException(ErrorCode.TODO_NOT_EXIST)

        if (goal.user != user || todo.goal != goal)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)

        if (todo.update(name, succeed))
            goal.todoSucceed()


        return TodoInfoDto(todo)
    }

    fun deleteTodo(userId: Long, goalId: Long, todoId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException(ErrorCode.GOAL_NOT_EXIST)
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotExistException(ErrorCode.TODO_NOT_EXIST)

        if (goal.user != user || todo.goal != goal)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)

        if (todo.succeed)
            throw CannotDeleteSucceedTodoException(ErrorCode.CANNOT_DELETE_SUCCEED_TODO)

        todoRepository.delete(todo)
    }
}