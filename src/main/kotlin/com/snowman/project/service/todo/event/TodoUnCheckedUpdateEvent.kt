package com.snowman.project.service.todo.event

import com.snowman.project.model.common.entity.DomainEvent
import com.snowman.project.model.todo.entity.Todo

class TodoUnCheckedUpdateEvent(source: Todo, message: String = "Todo Updated to Unchecked") :
    DomainEvent(source, message) {
}