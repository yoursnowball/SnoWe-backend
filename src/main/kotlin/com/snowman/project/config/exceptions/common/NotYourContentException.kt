package com.snowman.project.config.exceptions.common

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class NotYourContentException() : ApiException(ErrorCode.NOT_YOUR_CONTENT) {
}