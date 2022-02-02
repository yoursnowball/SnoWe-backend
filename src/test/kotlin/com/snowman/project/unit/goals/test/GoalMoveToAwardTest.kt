package com.snowman.project.unit.goals.test

import com.snowman.project.unit.goals.GoalTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GoalMoveToAwardTest : GoalTestBase() {

    @Test
    @DisplayName("Check Can Move To Award")
    fun checkMoveToAwardPossibilitiesTest() {
        val cantMoveToAwardGoal1 = getLevelDownGoal()
        val cantMoveToAwardGoal2 = getLevelKeepGoal()
        val cantMoveToAwardGoal3 = getLevelUpGoal()
        val canMoveToAwardGoal = getCanMoveToAwardGoal()

        assertEquals(false, cantMoveToAwardGoal1.canMoveToAwards())
        assertEquals(false, cantMoveToAwardGoal2.canMoveToAwards())
        assertEquals(false, cantMoveToAwardGoal3.canMoveToAwards())
        assertEquals(true, canMoveToAwardGoal.canMoveToAwards())
    }
}