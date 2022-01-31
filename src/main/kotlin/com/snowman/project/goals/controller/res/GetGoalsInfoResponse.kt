package com.snowman.project.goals.controller.res

import com.snowman.project.goals.domain.dto.SimpleGoalInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("목표 리스트")
class GetGoalsInfoResponse(
    @ApiModelProperty("목표 리스트")
    val goals: Map<String, SimpleGoalInfoDto>
) {
}