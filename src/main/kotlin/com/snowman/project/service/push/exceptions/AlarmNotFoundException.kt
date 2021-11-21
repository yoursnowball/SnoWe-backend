package com.snowman.project.service.push.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class AlarmNotFoundException() : ApiException(ErrorCode.ALARM_NOT_FOUND) {
}