package com.snowman.project.service.todo.event

import com.snowman.project.model.todo.entity.Todo
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class TodoEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleTodoUpdate(event: TodoCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal

        goal.todoChecked()
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleTodoUpdate(event: TodoUnCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal

        goal.todoUnchecked()
    }