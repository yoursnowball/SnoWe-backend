package com.snowman.project.auth.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class IncorrectUserInfoException() : ApiException(ErrorCode.WRONG_LOGIN_INFO) {
}