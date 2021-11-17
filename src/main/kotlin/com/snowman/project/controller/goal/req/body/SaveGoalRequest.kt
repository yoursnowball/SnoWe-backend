package com.snowman.project.controller.goal.req.body

import com.snowman.project.model.goal.enums.CharacterType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("목표 생성 요청")
data class SaveGoalRequest(
        @ApiModelProperty("목표 이름")
        val name: String,
        @ApiModelProperty("목표 캐릭터 종류")
        val type: CharacterType
) {
}