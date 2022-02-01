package com.snowman.project.unit.goals.test

import com.snowman.project.unit.goals.GoalServiceBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import java.util.Optional

class GoalLevelUpTest : GoalServiceBase() {

    @Test
    @DisplayName("Check LevelUp Possibility Test Success")
    fun checkLevelUpPossibilitiesTest() {
        val levelUpGoal = getLevelUpGoal()
        val levelKeepGoal = getLevelKeepGoal()
        val levelDownGoal = getLevelKeepGoal()

        assertEquals(true, levelUpGoal.isLevelUp())
        assertEquals(false, levelKeepGoal.isLevelUp())
        assertEquals(false, levelDownGoal.isLevelUp())
    }

    @Test
    @DisplayName("Todo Success에 따른 LevelUp 성공 (LevelUp)")
    fun toDoSuccessAndLevelUpSuccessTest() {
        val targetGoal = getLevelUpGoal()
        val previousLevel = targetGoal.level

        given(goalRepository.findById(anyLong()))
            .willReturn(Optional.of(targetGoal))

        assertDoesNotThrow { goalService.todoSuccess(1L) }
        assertEquals(targetGoal.level, previousLevel + 1)
        assertEquals(0, targetGoal.levelTodoCount)
        assertEquals(1, targetGoal.succeedTodoCount)
    }

    @Test
    @DisplayName("Todo Success에 따른 LevelUp 실패 (LevelKeep)")
    fun toDoSuccessAndLevelUpFailTest() {
        val targetGoal = getLevelDownGoal()
        val previousLevel = targetGoal.level

        given(goalRepository.findById(anyLong()))
            .willReturn(Optional.of(targetGoal))

        assertDoesNotThrow { goalService.todoSuccess(1L) }
        assertEquals(targetGoal.level, previousLevel)
        assertEquals(1, targetGoal.levelTodoCount)
        assertEquals(2, targetGoal.succeedTodoCount)
    }
}