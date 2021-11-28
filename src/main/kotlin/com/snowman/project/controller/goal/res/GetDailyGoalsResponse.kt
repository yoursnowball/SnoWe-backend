package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.DailyGoalInfoDto

data class GetDailyGoalsResponse(
    val goals: List<DailyGoalInfoDto>
) {
}