package com.snowman.project.dao.goal

import com.querydsl.core.QueryResults
import com.querydsl.core.Tuple
import com.snowman.project.model.user.entity.User

interface GoalRepositoryCustom {
    fun getBestDailyGoalsByDates(user: User): QueryResults<Tuple>
}