package com.snowman.project.unit.awards.test

import com.snowman.project.awards.service.exceptions.CannotMoveToAwardsException
import com.snowman.project.common.exception.DeletedContentException
import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.unit.awards.AwardsTestBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import java.util.Optional

class SaveAwardTest : AwardsTestBase() {

    @Test
    @DisplayName("Save Award Success")
    fun saveAwardSuccess() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getCanMoveToAwardGoal()))
        given(awardRepository.save(any())).willReturn(getAward())

        assertDoesNotThrow { awardService.saveAwards(1L, 1L) }
    }

    @Test
    @DisplayName("Save Award Fail Because Goal Level Is Not Enough")
    fun saveAwardFailBecauseGoalLevelIsNotEnough() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getCannotMoveToAwardGoal()))

        assertThrows<CannotMoveToAwardsException> { awardService.saveAwards(1L, 1L) }
    }

    @Test
    @DisplayName("Save Award Fail Because Not My Content")
    fun saveAwardFailBecauseGoalIsNotMyOwn() {
        given(userRepository.findById(2L)).willReturn(Optional.of(getNonOwnedUser()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getCannotMoveToAwardGoal()))

        assertThrows<NotYourContentException> { awardService.saveAwards(2L, 1L) }
    }

    @Test
    @DisplayName("Save Award Fail Because Already DeletedContent")
    fun saveAwardFailBecauseGoalIsAlreadyDeleted() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getDeletedGoal()))

        assertThrows<DeletedContentException> { awardService.saveAwards(1L, 1L) }
    }
}