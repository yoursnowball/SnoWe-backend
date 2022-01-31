package com.snowman.project.todos.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class CannotAddTodoException : ApiException(ErrorCode.CANNOT_ADD_TODO) {
}