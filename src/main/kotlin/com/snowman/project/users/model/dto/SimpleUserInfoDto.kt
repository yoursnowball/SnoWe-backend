package com.snowman.project.users.model.dto

import java.time.LocalTime

data class SimpleUserInfoDto(
        val nickName: String,
        val alarmTime: LocalTime,
)
