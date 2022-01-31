package com.snowman.project.todos.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class CannotDeleteSucceedTodoException() : ApiException(ErrorCode.CANNOT_DELETE_SUCCEED_TODO) {
}