package com.snowman.project.common.exception

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class NotYourContentException() : ApiException(ErrorCode.NOT_YOUR_CONTENT) {
}