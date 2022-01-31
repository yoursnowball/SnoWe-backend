package com.snowman.project.users.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class UserNotExistException() : ApiException(ErrorCode.USER_NOT_EXIST) {
}