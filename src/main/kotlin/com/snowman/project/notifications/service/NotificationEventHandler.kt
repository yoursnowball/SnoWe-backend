package com.snowman.project.notifications.service

import com.snowman.project.goals.domain.event.GoalLevelUpEvent
import com.snowman.project.notifications.domain.enums.PushType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventHandler(
    private val pushService: PushService
) {
    @EventListener
    fun goalLevelUpEventHandler(event: GoalLevelUpEvent) {
        pushService.sendPushMessage(event.userId, event.goalId, PushType.LEVELUP)
    }
}