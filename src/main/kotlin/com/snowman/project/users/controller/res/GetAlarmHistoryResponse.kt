package com.snowman.project.users.controller.res

import com.snowman.project.notifications.domain.dto.PushHistoryDto
import com.snowman.project.notifications.domain.enums.PushStatus
import java.time.LocalDateTime

class GetAlarmHistoryResponse(
    val id: Long,
    val title: String,
    val body: String,
    val status: PushStatus,
    val alarmAt: LocalDateTime
) {
    constructor(dto: PushHistoryDto) : this(
        dto.id,
        dto.title,
        dto.body,
        dto.status,
        dto.alarmAt
    )
}