package com.snowman.project.unit.todos.factory

import com.snowman.project.todos.domain.entity.Todo
import com.snowman.project.unit.goals.factory.GoalFactory
import com.snowman.project.unit.users.factory.UserFactory
import java.time.LocalDate
import java.time.LocalDateTime

class TodoFactory {
    private val goalFactory: GoalFactory = GoalFactory()
    private val userFactory: UserFactory = UserFactory()

    fun getPastTodo(): Todo {
        return Todo(
            id = 1L,
            goal = goalFactory.getLevelKeepGoal(),
            user = userFactory.getContentsOwnedUser(),
            name = "I'm Past Todo",
            createdAt = LocalDateTime.now(),
            todoDate = LocalDate.now().minusDays(1)
        )
    }

    fun getTodayTodo(): Todo {
        return Todo(
            id = 1L,
            goal = goalFactory.getLevelKeepGoal(),
            user = userFactory.getContentsOwnedUser(),
            name = "I'm today Todo",
            createdAt = LocalDateTime.now(),
            todoDate = LocalDate.now()
        )
    }

    fun getFutureTodo(): Todo {
        return Todo(
            id = 1L,
            goal = goalFactory.getLevelKeepGoal(),
            user = userFactory.getContentsOwnedUser(),
            name = "I'm today Todo",
            createdAt = LocalDateTime.now(),
            todoDate = LocalDate.now().plusDays(1)
        )
    }

    fun getAlreadySuccessTodo(): Todo {
        return Todo(
            id = 1L,
            goal = goalFactory.getLevelKeepGoal(),
            user = userFactory.getContentsOwnedUser(),
            name = "I'm today Todo",
            todoDate = LocalDate.now().plusDays(1),
            succeed = true,
            createdAt = LocalDateTime.now().minusMinutes(20),
            finishedAt = LocalDateTime.now()
        )
    }
}