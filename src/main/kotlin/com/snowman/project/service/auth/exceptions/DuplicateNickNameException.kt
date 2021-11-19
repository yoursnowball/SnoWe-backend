package com.snowman.project.service.auth.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class DuplicateNickNameException : ApiException(ErrorCode.DUPLICATE_NICKNAME) {
}