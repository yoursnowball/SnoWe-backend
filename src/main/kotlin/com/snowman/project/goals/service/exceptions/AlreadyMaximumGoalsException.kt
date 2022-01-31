package com.snowman.project.goals.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class AlreadyMaximumGoalsException : ApiException(ErrorCode.ALREADY_MAXIMUM_GOALS) {
}