package com.snowman.project.users.controller.res

import com.snowman.project.notifications.domain.dto.PushHistoryDto

data class GetAlarmHistoriesResponse(
    val alarms: List<PushHistoryDto>
) {
}