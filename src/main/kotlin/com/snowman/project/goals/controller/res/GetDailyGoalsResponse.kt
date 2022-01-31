package com.snowman.project.goals.controller.res

import com.snowman.project.goals.domain.dto.DailyGoalInfoDto

data class GetDailyGoalsResponse(
    val goals: List<DailyGoalInfoDto>
) {
}