package com.snowman.project.service.todo.event

import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.model.todo.entity.Todo
import com.snowman.project.service.push.PushService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TodoEventHandler(
    private val todoRepository: TodoRepository,
    private val goalRepository: GoalRepository,
    private val pushService: PushService
) {

    @EventListener
    fun todoCheckedUpdateHandler(event: TodoCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal
        val user = goal.user

        goalRepository.save(goal.todoChecked())
        if (todoRepository.countAllByGoalAndTodoDateAndSucceedIsFalse(goal, LocalDate.now()) == 0)
            pushService.saveAlarmMessage(user, PushType.ALLCLEAR, SimpleGoalInfoDto(goal))
    }

    @EventListener
    fun todoUncheckedUpdateHandler(event: TodoUnCheckedUpdateEvent) {
        val todo = event.source as Todo
        val goal = todo.goal

        goalRepository.save(goal.todoUnchecked())
    }
}