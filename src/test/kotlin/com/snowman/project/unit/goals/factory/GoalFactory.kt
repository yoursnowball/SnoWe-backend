package com.snowman.project.unit.goals.factory

import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.domain.enums.CharacterType
import com.snowman.project.unit.users.factory.UserFactory
import java.time.LocalDateTime

class GoalFactory {
    private val userFactory = UserFactory()

    fun getLevelUpGoal(): Goal {
        return Goal(
            id = 1L,
            name = "levelUpGoal",
            objective = "testIsGoalLevelUp",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }

    fun getLevelDownGoal(): Goal {
        return Goal(
            id = 1L,
            level = 2,
            succeedTodoCount = 1,
            levelTodoCount = 0,
            name = "levelDownGoal",
            objective = "testIsGoalLevelDown",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }

    fun getLevelKeepGoal(): Goal {
        return Goal(
            id = 1L,
            level = 2,
            succeedTodoCount = 2,
            levelTodoCount = 1,
            name = "levelKeepGoal",
            objective = "testIsGoalLevelKeep",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }

    fun getCanMoveToAwardGoal(): Goal {
        return Goal(
            id = 1L,
            level = 5,
            succeedTodoCount = 100,
            levelTodoCount = 0,
            name = "MoveToAwardGoal",
            objective = "testCanGoalMoveToAward",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }

    fun getDeletedGoal(): Goal {
        return Goal(
            id = 1L,
            deleted = true,
            name = "MoveToAwardGoal",
            objective = "testCanGoalMoveToAward",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }

    fun getAwardedGoal(): Goal {
        return Goal(
            id = 1L,
            awarded = true,
            succeedTodoCount = 150,
            name = "MoveToAwardGoal",
            objective = "testCanGoalMoveToAward",
            characterType = CharacterType.BLUE,
            user = userFactory.getContentsOwnedUser(),
            createdAt = LocalDateTime.now()
        )
    }
}