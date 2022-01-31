package com.snowman.project.goals.controller.req

import com.snowman.project.goals.domain.enums.CharacterType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@ApiModel("목표 생성 요청")
data class SaveGoalRequest(
    @ApiModelProperty("목표 이름")
    @field:NotEmpty
    val name: String,

    @ApiModelProperty("목표 설정")
    @field:NotEmpty
    val objective: String,

    @ApiModelProperty("목표 캐릭터 종류")
    @field:NotNull
    val type: CharacterType
) {
}