package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.DetailGoalInfoDto

data class GetDetailGoalsResponse(
    val goals: List<DetailGoalInfoDto>
) {
}