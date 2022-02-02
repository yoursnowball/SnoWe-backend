package com.snowman.project.unit.todos

import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.todos.domain.entity.Todo
import com.snowman.project.todos.service.TodoService
import com.snowman.project.unit.goals.factory.GoalFactory
import com.snowman.project.unit.todos.factory.TodoFactory
import com.snowman.project.unit.users.factory.UserFactory
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockitoExtension::class)
open class TodoTestBase {

    private val userFactory: UserFactory = UserFactory()
    private val todoFactory: TodoFactory = TodoFactory()
    private val goalFactory: GoalFactory = GoalFactory()

    @Mock
    lateinit var todoRepository: TodoRepository

    @Mock
    lateinit var goalRepository: GoalRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var publisher: ApplicationEventPublisher

    @InjectMocks
    lateinit var todoService: TodoService

    fun getPastTodo(): Todo {
        return todoFactory.getPastTodo()
    }

    fun getTodayTodo(): Todo {
        return todoFactory.getTodayTodo()
    }

    fun getFutureTodo(): Todo {
        return todoFactory.getFutureTodo()
    }

    fun getContentOwner(): User {
        return userFactory.getContentsOwnedUser()
    }

    fun getNonOwnedUser(): User {
        return userFactory.getNonOwnedUser()
    }

    fun getLevelUpGoal(): Goal {
        return goalFactory.getLevelUpGoal()
    }

    fun getCanDeleteTodo(): Todo {
        return todoFactory.getTodayTodo()
    }

    fun getAlreadySuccessTodo(): Todo {
        return todoFactory.getAlreadySuccessTodo()
    }

    fun getDeletedGoal(): Goal {
        return goalFactory.getDeletedGoal()
    }

    fun getAwardedGoal(): Goal {
        return goalFactory.getAwardedGoal()
    }
}