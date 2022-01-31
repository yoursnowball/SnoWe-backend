package com.snowman.project.goals.dao

import com.snowman.project.goals.dao.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.users.model.entity.User
import java.time.LocalDate

interface GoalRepositoryCustom {
    fun getDailyGoalsWithSucceedTodoCountByDateBetween(
        user: User,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<DailyGoalAndSucceedTodoNumDto>

    fun getActiveGoalsByDateAndUser(user: User, date: LocalDate): List<Goal>
}