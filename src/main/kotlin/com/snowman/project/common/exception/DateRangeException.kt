package com.snowman.project.common.exception

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class DateRangeException() : ApiException(ErrorCode.DATE_RANGE_EXCEPTION) {
}