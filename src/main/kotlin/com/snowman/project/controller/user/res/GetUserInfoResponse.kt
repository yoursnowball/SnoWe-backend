package com.snowman.project.controller.user.res

import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.user.dto.DetailUserInfoDto
import java.time.LocalDateTime
import java.time.LocalTime

data class GetUserInfoResponse(
    val nickName: String,
    val alarmTime: LocalTime?,
    val createdAt: LocalDateTime,
    val goals: List<SimpleGoalInfoDto>
) {
    constructor(dto: DetailUserInfoDto, goals: List<SimpleGoalInfoDto>) : this(
        dto.nickName,
        dto.alarmTime,
        dto.createdAt,
        goals
    )
}