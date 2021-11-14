package com.snowman.project.service.auth.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class DuplicateUserNameException(errorCode:ErrorCode) : ApiException(errorCode) {
}