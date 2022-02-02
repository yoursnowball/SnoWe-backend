package com.snowman.project.unit.goals.test

import com.snowman.project.goals.domain.enums.CharacterType
import com.snowman.project.goals.service.exceptions.AlreadyMaximumGoalsException
import com.snowman.project.unit.goals.GoalTestBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import java.util.Optional

class SaveGoalTest : GoalTestBase() {
    @Test
    @DisplayName("Save Goal Failed Because Already Have 4 goals")
    fun saveGoalFailedBecauseAlreadyHave4Goals() {
        val user = getContentOwner()
        given(userRepository.findById(1L)).willReturn(Optional.of(user))
        given(goalRepository.countAllByUserAndDeletedIsFalseAndAwardedIsFalse(user))
            .willReturn(4)
        assertThrows<AlreadyMaximumGoalsException> {
            goalService.saveGoal(
                1L,
                "saveGoal",
                "saveGoal",
                CharacterType.GREEN
            )
        }
    }
}