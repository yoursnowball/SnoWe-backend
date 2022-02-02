package com.snowman.project.unit.awards.factory

import com.snowman.project.awards.domain.entity.Award
import com.snowman.project.unit.goals.factory.GoalFactory
import com.snowman.project.unit.users.factory.UserFactory
import java.time.LocalDateTime

class AwardFactory {
    private val userFactory: UserFactory = UserFactory()
    private val goalFactory: GoalFactory = GoalFactory()

    fun getAward(): Award {
        return Award(
            id = 1L,
            goal = goalFactory.getCanMoveToAwardGoal(),
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now(),
            totalTodoCount = 500
        )
    }
}