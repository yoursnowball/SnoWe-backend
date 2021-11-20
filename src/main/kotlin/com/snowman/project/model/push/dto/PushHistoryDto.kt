package com.snowman.project.model.push.dto

import com.snowman.project.model.push.entity.PushMessage
import com.snowman.project.model.push.enums.PushStatus
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