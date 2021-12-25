package com.snowman.project.service.goal.event

import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.service.push.PushService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.LocalDate

@Component
class GoalEventListener(
    val todoRepository: TodoRepository,
    val pushService: PushService
) {

    @EventListener
    fun goalLevelUpEventHandler(event: GoalLevelUpEvent) {
        val goal = event.source as Goal
        val user = goal.user
        pushService.saveAlarmMessage(user, PushType.LEVELUP, SimpleGoalInfoDto(goal))

        if (todoRepository.countAllByGoalAndTodoDateAndSucceedIsFalse(goal, LocalDate.now()) == 0)
            pushService.saveAlarmMessage(user, PushType.ALLCLEAR, SimpleGoalInfoDto(goal))
    }
}