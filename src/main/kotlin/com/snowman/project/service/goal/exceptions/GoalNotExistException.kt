package com.snowman.project.service.goal.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class GoalNotExistException() : ApiException(ErrorCode.GOAL_NOT_EXIST) {
}