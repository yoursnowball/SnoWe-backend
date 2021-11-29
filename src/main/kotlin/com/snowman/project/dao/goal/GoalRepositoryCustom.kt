package com.snowman.project.dao.goal

import com.snowman.project.dao.goal.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import java.time.LocalDate

interface GoalRepositoryCustom {
    fun getDailyGoalsWithSucceedTodoCountByDateBetween(
        user: User,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<DailyGoalAndSucceedTodoNumDto>

    fun getActiveGoalsByDateAndUser(user: User, date: LocalDate): List<Goal>
}