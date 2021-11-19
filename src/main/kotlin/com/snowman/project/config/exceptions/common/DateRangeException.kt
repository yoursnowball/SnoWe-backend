package com.snowman.project.config.exceptions.common

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class DateRangeException() : ApiException(ErrorCode.DATE_RANGE_EXCEPTION) {
}