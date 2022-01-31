package com.snowman.project.auth.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class DuplicateNickNameException : ApiException(ErrorCode.DUPLICATE_NICKNAME) {
}