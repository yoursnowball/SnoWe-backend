package com.snowman.project.service.user.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class UserNotExistException() : ApiException(ErrorCode.USER_NOT_EXIST) {
}