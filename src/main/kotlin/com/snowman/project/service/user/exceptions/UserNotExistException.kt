package com.snowman.project.service.user.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class UserNotExistException(errorCode: ErrorCode) : ApiException(errorCode) {
}