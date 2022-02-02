package com.snowman.project.unit.todos.test

import com.snowman.project.unit.todos.TodoTestBase
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CheckTodoEditableTest : TodoTestBase() {

    @Test
    @DisplayName("Check Edit Past todo is Possible Test")
    fun checkCanPastTodoEdit() {
        val pastTodo = getPastTodo()
        assertFalse(pastTodo.canUpdateOrDelete())
    }

    @Test
    @DisplayName("Check Edit Today Todo is Possible Test")
    fun checkCanTodayTodoEdit() {
        val todayTodo = getTodayTodo()
        assertTrue(todayTodo.canUpdateOrDelete())
    }

    @Test
    @DisplayName("Check Edit Todo is Possible Test")
    fun checkCanFutureTodoEdit() {
        val futureTodo = getFutureTodo()
        assertTrue(futureTodo.canUpdateOrDelete())
    }
}