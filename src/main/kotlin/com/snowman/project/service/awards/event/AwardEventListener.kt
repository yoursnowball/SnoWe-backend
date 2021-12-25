package com.snowman.project.service.awards.event

import com.snowman.project.model.awards.entity.Award
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AwardEventListener {


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleAwardSaveEvent(event: AwardSaveEvent) {
        val award = event.source as Award
        award.goal.moveToAward()

    }
}