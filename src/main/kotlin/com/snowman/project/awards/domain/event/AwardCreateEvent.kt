package com.snowman.project.awards.domain.event

import com.snowman.project.common.domain.event.DomainEvent

class AwardCreateEvent(val goalId: Long) : DomainEvent {
}