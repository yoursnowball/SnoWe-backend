package com.snowman.project.goals.service

import com.snowman.project.awards.domain.event.AwardCreateEvent
import com.snowman.project.todos.domain.event.TodoCheckedEvent
import com.snowman.project.todos.domain.event.TodoUncheckedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager

@Component
class GoalEventHandler(
    val goalService: GoalService
) {

    @EventListener
    fun todoCheckedEventHandler(event: TodoCheckedEvent) {
        goalService.todoSuccess(event.goalId)
    }

    @EventListener
    fun todoUnCheckedEventHandler(event: TodoUncheckedEvent) {
        goalService.todoFail(event.goalId)
    }

    @EventListener
    fun awardCreateEventHandler(event: AwardCreateEvent) {
        goalService.goalMoveToAward(event.goalId)
    }
}