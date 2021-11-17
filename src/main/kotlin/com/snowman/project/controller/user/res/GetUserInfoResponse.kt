package com.snowman.project.controller.user.res

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
        @ApiModelProperty("푸시 알람 시간 (HH:mm)")
        val alarmTime: LocalTime?,
        @ApiModelProperty("가입 일 (yyyy-MM-dd HH:mm:ss)")
        val createdAt: LocalDateTime,
        @ApiModelProperty("목표들")
        val goals: List<SimpleGoalInfoDto>
) {
    constructor(dto: DetailUserInfoDto, goals: List<SimpleGoalInfoDto>) : this(
            dto.nickName,
            dto.alarmTime,
            dto.createdAt,
            goals
    )
}