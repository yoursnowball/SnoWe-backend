package com.snowman.project.service.awards.event

import com.snowman.project.model.awards.entity.Award
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AwardEventListener {

    @EventListener
    fun handleAwardSaveEvent(event: AwardSaveEvent) {
        val award = event.source as Award
        val goal = award.goal
        goal.moveToAward()

    }
}