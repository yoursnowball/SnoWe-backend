package com.snowman.project.unit.goals.test

import com.snowman.project.common.exception.DeletedContentException
import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.unit.goals.GoalTestBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import java.time.LocalDate
import java.util.Optional

class GetMyGoalsTest : GoalTestBase() {

    @Test
    @DisplayName("Get GoalsFailed Because Not My Content")
    fun getGoalsFailedBecauseNotMyContent() {
        given(userRepository.findById(2L)).willReturn(
            Optional.of(getNonOwnedUser())
        )
        given(goalRepository.findById(1L)).willReturn(
            Optional.of(getLevelUpGoal())
        )
        assertThrows<NotYourContentException> { goalService.getMyGoal(2L, 1L, LocalDate.now()) }
    }

    @Test
    @DisplayName("Get GoalsFailed Because Content was deleted")
    fun getGoalsFailedBecauseContentWasDeleted() {
        val contentOwner = getContentOwner()
        given(userRepository.findById(1L)).willReturn(
            Optional.of(contentOwner)
        )
        given(goalRepository.findById(1L)).willReturn(
            Optional.of(getDeletedContent())
        )
        assertThrows<DeletedContentException> { goalService.getMyGoal(1L, 1L, LocalDate.now()) }
    }
}