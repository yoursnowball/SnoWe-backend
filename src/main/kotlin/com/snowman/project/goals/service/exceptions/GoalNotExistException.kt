package com.snowman.project.goals.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class GoalNotExistException() : ApiException(ErrorCode.GOAL_NOT_EXIST) {
}