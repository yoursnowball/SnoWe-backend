package com.snowman.project.service.todo.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class CannotDeleteSucceedTodoException() : ApiException(ErrorCode.CANNOT_DELETE_SUCCEED_TODO) {
}