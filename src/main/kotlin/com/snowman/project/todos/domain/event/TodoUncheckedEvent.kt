package com.snowman.project.todos.domain.event

import com.snowman.project.common.domain.event.DomainEvent

class TodoUncheckedEvent(
    val goalId: Long
) : DomainEvent {
}