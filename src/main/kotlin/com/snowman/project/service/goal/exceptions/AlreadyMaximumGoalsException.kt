package com.snowman.project.service.goal.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class AlreadyMaximumGoalsException : ApiException(ErrorCode.ALREADY_MAXIMUM_GOALS) {
}