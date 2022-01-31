package com.snowman.project.auth.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class DuplicateUserNameException() : ApiException(ErrorCode.DUPLICATE_USERNAME) {
}