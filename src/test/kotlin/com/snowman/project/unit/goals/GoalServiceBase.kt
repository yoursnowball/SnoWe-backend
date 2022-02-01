package com.snowman.project.unit.goals

import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.service.GoalService
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.unit.goals.factory.GoalFactory
import com.snowman.project.unit.users.factory.UserFactory
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher


@ExtendWith(MockitoExtension::class)
open class GoalServiceBase(

) {
    @Mock
    lateinit var publisher: ApplicationEventPublisher

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var goalRepository: GoalRepository

    @Mock
    lateinit var todoRepository: TodoRepository

    @InjectMocks
    lateinit var goalService: GoalService

    private val goalFactory: GoalFactory = GoalFactory()

    private val userFactory: UserFactory = UserFactory()

    fun getContentOwner(): User {
        return userFactory.getContentsOwnedUser()
    }

    fun getNonOwnedUser(): User {
        return userFactory.getNonOwnedUser()
    }

    fun getLevelUpGoal(): Goal {
        return goalFactory.getLevelUpGoal()
    }

    fun getLevelDownGoal(): Goal {
        return goalFactory.getLevelDownGoal()
    }

    fun getLevelKeepGoal(): Goal {
        return goalFactory.getLevelKeepGoal()
    }

    fun getCanMoveToAwardGoal(): Goal {
        return goalFactory.getCanMoveToAwardGoal()
    }

    fun getDeletedContent(): Goal {
        return goalFactory.getDeletedGoal()
    }
}