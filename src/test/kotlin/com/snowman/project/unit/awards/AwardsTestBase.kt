package com.snowman.project.unit.awards

import com.snowman.project.awards.dao.AwardRepository
import com.snowman.project.awards.domain.entity.Award
import com.snowman.project.awards.service.AwardService
import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.unit.awards.factory.AwardFactory
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
open class AwardsTestBase {

    @Mock
    lateinit var awardRepository: AwardRepository

    @Mock
    lateinit var goalRepository: GoalRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var todoRepository: TodoRepository

    @Mock
    lateinit var publisher: ApplicationEventPublisher

    @InjectMocks
    lateinit var awardService: AwardService

    private val awardFactory: AwardFactory = AwardFactory()

    private val userFactory: UserFactory = UserFactory()

    private val goalFactory: GoalFactory = GoalFactory()

    fun getContentOwner(): User {
        return userFactory.getContentsOwnedUser()
    }

    fun getNonOwnedUser(): User {
        return userFactory.getNonOwnedUser()
    }

    fun getAward(): Award {
        return awardFactory.getAward()
    }

    fun getCanMoveToAwardGoal(): Goal {
        return goalFactory.getCanMoveToAwardGoal()
    }

    fun getDeletedGoal(): Goal {
        return goalFactory.getDeletedGoal()
    }

    fun getCannotMoveToAwardGoal(): Goal {
        return goalFactory.getLevelDownGoal()
    }
}