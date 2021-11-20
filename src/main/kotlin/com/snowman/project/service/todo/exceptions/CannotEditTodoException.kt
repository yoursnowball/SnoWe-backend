package com.snowman.project.service.todo.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class CannotEditTodoException : ApiException(ErrorCode.CANNOT_EDIT_PAST_TODO) {
}