package com.snowman.project.service.goal.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class CannotDeleteGoalException : ApiException(ErrorCode.CANNOT_DELETE_GOAL) {
}