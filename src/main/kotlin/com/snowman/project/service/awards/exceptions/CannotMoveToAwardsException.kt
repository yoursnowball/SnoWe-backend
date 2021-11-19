package com.snowman.project.service.awards.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class CannotMoveToAwardsException : ApiException(ErrorCode.CANNOT_MOVE_TO_AWARDS) {
}