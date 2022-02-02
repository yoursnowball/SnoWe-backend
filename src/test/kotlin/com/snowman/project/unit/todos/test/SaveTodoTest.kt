package com.snowman.project.unit.todos.test

import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.todos.service.exceptions.CannotAddTodoException
import com.snowman.project.unit.todos.TodoTestBase
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import java.time.LocalDate
import java.util.Optional

class SaveTodoTest : TodoTestBase() {
    @Test
    @DisplayName("Update Todo Success")
    fun saveTodoSuccessTest() {
        val targetUser = getContentOwner()
        val targetGoal = getLevelUpGoal()
        given(userRepository.findById(1L)).willReturn(Optional.of(targetUser))
        given(goalRepository.findById(1L)).willReturn(Optional.of(targetGoal))
        given(todoRepository.findAllByGoalAndTodoDate(targetGoal, LocalDate.now())).willReturn(listOf(getTodayTodo()))

        val result = assertDoesNotThrow { todoService.saveTodos(1L, 1L, "I'm  today Todo", LocalDate.now()) }
        assertFalse(result.isEmpty())
    }

    @Test
    @DisplayName("Save Todo Fail Because Not My Content")
    fun saveTodoFailBecauseNotMyContent() {
        given(userRepository.findById(2L)).willReturn(Optional.of(getNonOwnedUser()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getLevelUpGoal()))

        assertThrows<NotYourContentException> { todoService.saveTodos(2L, 1L, "NotMyTodo", LocalDate.now()) }
    }

    @Test
    @DisplayName("Can't Save Todo Because Goal Was Deleted")
    fun saveTodoFailBecauseGoalWasDeleted() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(Optional.of(getDeletedGoal()))

        assertThrows<CannotAddTodoException> { todoService.saveTodos(1L, 1L, "cannotSaveTodo", LocalDate.now()) }
    }

    @Test
    @DisplayName("Can't Save Todo Because Goal Was Deleted")
    fun saveTodoFailBecauseTodoWasAwarded() {
        given(userRepository.findById(1L)).willReturn(Optional.of(getContentOwner()))
        given(goalRepository.findById(1L)).willReturn(
            Optional.of(getAwardedGoal())
        )

        assertThrows<CannotAddTodoException> {
            todoService.saveTodos(
                1L,
                1L,
                "cannotSaveTodo",
                LocalDate.now()
            )
        }
    }

}