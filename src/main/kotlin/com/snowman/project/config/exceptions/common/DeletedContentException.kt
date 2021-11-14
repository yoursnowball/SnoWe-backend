package com.snowman.project.config.exceptions.common

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class DeletedContentException(errorCode:ErrorCode) : ApiException(errorCode) {
}