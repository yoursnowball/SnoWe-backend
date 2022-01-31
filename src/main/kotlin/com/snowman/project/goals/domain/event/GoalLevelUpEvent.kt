package com.snowman.project.goals.domain.event

import com.snowman.project.common.domain.event.DomainEvent

class GoalLevelUpEvent(val userId: Long, val goalId: Long) : DomainEvent {
}