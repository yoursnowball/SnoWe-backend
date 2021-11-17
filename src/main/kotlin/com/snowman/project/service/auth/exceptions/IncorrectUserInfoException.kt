package com.snowman.project.service.auth.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class IncorrectUserInfoException() : ApiException(ErrorCode.WRONG_LOGIN_INFO) {
}