package com.snowman.project.service.goal.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class GoalNotExistException(errorCode: ErrorCode) : ApiException(errorCode) {
}