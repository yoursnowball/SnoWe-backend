package com.snowman.project.unit.todos.test

import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.todos.service.exceptions.CannotEditTodoException
import com.snowman.project.unit.todos.TodoTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import java.util.Optional

class UpdateTodoTest : TodoTestBase() {

    @Test
    @DisplayName("Update Todo Success")
    fun updateTodoSuccessTest() {
        val targetTodo = getTodayTodo()
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(targetTodo))

        assertDoesNotThrow { todoService.updateToDo(1L, 1L, 1L, "todayTodoComplete", true) }
        assertEquals(true, targetTodo.succeed)
        assertEquals("todayTodoComplete", targetTodo.name)

        assertDoesNotThrow { todoService.updateToDo(1L, 1L, 1L, "todayTodoNotCompletedYet", false) }
        assertEquals(false, targetTodo.succeed)
        assertEquals("todayTodoNotCompletedYet", targetTodo.name)
    }

    @Test
    @DisplayName("Update Todo Fail Because Not My Content")
    fun updateTodoFailBecauseNotMyTodo() {
        given(userRepository.findById(2L)).willReturn(Optional.of(getNonOwnedUser()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getCanDeleteTodo()))

        assertThrows<NotYourContentException> { todoService.updateToDo(2L, 1L, 1L, "NotMyTodo", true) }
    }

    @Test
    @DisplayName("Can't Delete Past Todo Test")
    fun updateTodoFailBecauseTodoIsPast() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getPastTodo()))

        assertThrows<CannotEditTodoException> { todoService.updateToDo(1L, 1L, 1L,"cannotUpdateTodo",true) }
    }
}