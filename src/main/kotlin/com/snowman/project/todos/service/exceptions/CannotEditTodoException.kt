package com.snowman.project.todos.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class CannotEditTodoException : ApiException(ErrorCode.CANNOT_EDIT_PAST_TODO) {
}