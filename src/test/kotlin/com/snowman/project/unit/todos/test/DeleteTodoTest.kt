package com.snowman.project.unit.todos.test

import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.todos.service.exceptions.CannotDeleteSucceedTodoException
import com.snowman.project.todos.service.exceptions.CannotEditTodoException
import com.snowman.project.unit.todos.TodoTestBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import java.util.Optional

class DeleteTodoTest : TodoTestBase() {

    @Test
    @DisplayName("Delete Todo Success")
    fun deleteTodoSuccessTest() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getCanDeleteTodo()))

        assertDoesNotThrow { todoService.deleteTodo(1L, 1L, 1L) }
    }

    @Test
    @DisplayName("Delete Todo Fail Because Not My Content")
    fun deleteTodoFailBecauseNotMyTodo() {
        given(userRepository.findById(2L)).willReturn(Optional.of(getNonOwnedUser()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getCanDeleteTodo()))

        assertThrows<NotYourContentException> { todoService.deleteTodo(2L, 1L, 1L) }
    }

    @Test
    @DisplayName("Can't Delete Past Todo Test")
    fun deleteTodoFailBecauseTodoIsPast() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getPastTodo()))

        assertThrows<CannotEditTodoException> { todoService.deleteTodo(1L, 1L, 1L) }
    }

    @Test
    @DisplayName("Can't Delete Already Succesd Todo")
    fun deleteTodoFailBecauseTodoWasAlreadySucceed() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))
        given(todoRepository.findById(1L)).willReturn(Optional.of(getAlreadySuccessTodo()))

        assertThrows<CannotDeleteSucceedTodoException> { todoService.deleteTodo(1L, 1L, 1L) }
    }
}