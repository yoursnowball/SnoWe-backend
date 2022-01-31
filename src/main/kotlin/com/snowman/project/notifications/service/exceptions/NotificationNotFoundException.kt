package com.snowman.project.notifications.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class NotificationNotFoundException() : ApiException(ErrorCode.ALARM_NOT_FOUND) {
}