package com.snowman.project.service.goal.event

import com.snowman.project.model.common.entity.DomainEvent
import com.snowman.project.model.goal.entity.Goal

class GoalLevelUpEvent(source: Goal, message: String = "Goal LevelUp") : DomainEvent(source, message) {
}