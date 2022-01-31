package com.snowman.project.users.model.dto

import java.time.LocalDateTime
import java.time.LocalTime

data class DetailUserInfoDto(
        val nickName: String,
        val createdAt: LocalDateTime
) {
}