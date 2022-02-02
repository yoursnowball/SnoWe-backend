package com.snowman.project.unit.goals.test

import com.snowman.project.unit.goals.GoalTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.BDDMockito
import java.util.Optional
import kotlin.math.pow

class GoalLevelDownTest : GoalTestBase() {

    @Test
    @DisplayName("Check LevelDown Possibility Test Success (Level이 1보다 낮아질 수 없음)")
    fun checkLevelDownPossibilitiesTest() {
        val levelUpGoal = getLevelUpGoal()
        val levelKeepGoal = getLevelKeepGoal()
        val levelDownGoal = getLevelDownGoal()

        assertEquals(false, levelUpGoal.isLevelDown())
        assertEquals(false, levelKeepGoal.isLevelDown())
        assertEquals(true, levelDownGoal.isLevelDown())
    }


    @Test
    @DisplayName("Todo Fail 에 따른 LevelDown 성공 (LevelDown)")
    fun toDoSuccessAndLevelUpFailTest() {
        val targetGoal = getLevelDownGoal()
        val previousLevel = targetGoal.level
        val previousSuccessTodoCount = targetGoal.succeedTodoCount

        BDDMockito.given(goalRepository.findById(BDDMockito.anyLong()))
            .willReturn(Optional.of(targetGoal))

        assertDoesNotThrow { goalService.todoFail(1L) }
        assertEquals(previousLevel, targetGoal.level + 1)
        assertEquals(((previousLevel - 1).toDouble().pow(3).toInt()) - 1, targetGoal.levelTodoCount)
        assertEquals(previousSuccessTodoCount, targetGoal.succeedTodoCount + 1)
    }

    @Test
    @DisplayName("Todo Fail에 성공 (LevelKeep)")
    fun toDoSuccessAndLevelUpSuccessTest() {
        val targetGoal = getLevelKeepGoal()
        val previousLevel = targetGoal.level
        val previousSuccessTodoCount = targetGoal.succeedTodoCount
        val previousLevelTodoCount = targetGoal.levelTodoCount

        BDDMockito.given(goalRepository.findById(BDDMockito.anyLong()))
            .willReturn(Optional.of(targetGoal))

        assertDoesNotThrow { goalService.todoFail(1L) }
        assertEquals(targetGoal.level, previousLevel)
        assertEquals(previousLevelTodoCount - 1, targetGoal.levelTodoCount)
        assertEquals(previousSuccessTodoCount - 1, targetGoal.succeedTodoCount)
    }
}
