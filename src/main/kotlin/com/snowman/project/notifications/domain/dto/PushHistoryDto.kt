package com.snowman.project.notifications.domain.dto

import com.snowman.project.notifications.domain.entity.PushMessage
import com.snowman.project.notifications.domain.enums.PushStatus
import java.time.LocalDateTime

data class PushHistoryDto(
    val id: Long,
    val title: String,
    val body: String,
    val status: PushStatus,
    val alarmAt: LocalDateTime
) {
    constructor(message: PushMessage) : this(
        message.id!!,
        message.title,
        message.body,
        message.status,
        message.createdAt!!
    )
}