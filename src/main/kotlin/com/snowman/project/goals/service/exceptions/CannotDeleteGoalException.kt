package com.snowman.project.goals.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class CannotDeleteGoalException : ApiException(ErrorCode.CANNOT_DELETE_GOAL) {
}