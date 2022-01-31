package com.snowman.project.common.exception

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class DeletedContentException() : ApiException(ErrorCode.DELETED_CONTENT) {
}