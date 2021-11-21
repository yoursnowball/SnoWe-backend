package com.snowman.project.controller.user.res

import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.user.dto.DetailUserInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime
import java.time.LocalTime

@ApiModel("내 정보")
data class GetUserInfoResponse(
        @ApiModelProperty("닉네임")
        val nickName: String,
        @ApiModelProperty("가입 일 (yyyy-MM-dd HH:mm:ss)")
        val createdAt: LocalDateTime,
        @ApiModelProperty("목표들")
        val goals: List<DetailGoalInfoDto>
) {
    constructor(dto: DetailUserInfoDto, goals: List<DetailGoalInfoDto>) : this(
            dto.nickName,
            dto.createdAt,
            goals
    )
}