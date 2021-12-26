package com.snowman.project.service.goal.event

import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.service.push.PushService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class GoalEventListener(
    val pushService: PushService
) {

    @EventListener
    fun goalLevelUpEventHandler(event: GoalLevelUpEvent) {
        val goal = event.source as Goal
        val user = goal.user
        pushService.saveAlarmMessage(user, PushType.LEVELUP, SimpleGoalInfoDto(goal))
    }
}