package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("목표 리스트")
class GetGoalsResponse(
    @ApiModelProperty("목표 리스트")
    val goals: Map<String, SimpleGoalInfoDto>
) {
}