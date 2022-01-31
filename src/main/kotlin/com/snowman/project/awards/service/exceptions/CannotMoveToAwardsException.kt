package com.snowman.project.awards.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class CannotMoveToAwardsException : ApiException(ErrorCode.CANNOT_MOVE_TO_AWARDS) {
}