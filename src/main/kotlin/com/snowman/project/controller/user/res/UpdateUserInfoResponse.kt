package com.snowman.project.controller.user.res

import com.snowman.project.model.user.dto.SimpleUserInfoDto
import java.time.LocalTime

data class UpdateUserInfoResponse(
        val nickName: String,
        val alarmTime: LocalTime
) {
    constructor(dto: SimpleUserInfoDto) : this(dto.nickName, dto.alarmTime)
}