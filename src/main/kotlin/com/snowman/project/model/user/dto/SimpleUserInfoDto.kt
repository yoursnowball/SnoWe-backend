package com.snowman.project.model.user.dto

import java.time.LocalTime

data class SimpleUserInfoDto(
        val nickName: String,
        val alarmTime: LocalTime,
)
