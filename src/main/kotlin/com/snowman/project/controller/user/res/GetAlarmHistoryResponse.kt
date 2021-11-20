package com.snowman.project.controller.user.res

import com.snowman.project.model.push.dto.PushHistoryDto

data class GetAlarmHistoryResponse(
    val alarms: List<PushHistoryDto>
) {
}