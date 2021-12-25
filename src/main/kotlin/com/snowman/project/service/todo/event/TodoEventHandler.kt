package com.snowman.project.service.todo.event

import com.snowman.project.model.todo.entity.Todo
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class TodoEventHandler {

    @EventListener
    fun handleTodoUpdate(event: TodoCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal

        goal.todoChecked()
    }

    @EventListener
    fun handleTodoUpdate(event: TodoUnCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal

        goal.todoUnchecked()
    }
}