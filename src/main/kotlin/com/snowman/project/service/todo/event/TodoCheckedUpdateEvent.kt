package com.snowman.project.service.todo.event

import com.snowman.project.model.common.entity.DomainEvent
import com.snowman.project.model.todo.entity.Todo

class TodoCheckedUpdateEvent(source: Todo, message: String = "Todo Updated to Checked") : DomainEvent(source, message) {
}