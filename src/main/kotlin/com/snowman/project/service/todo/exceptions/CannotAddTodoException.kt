package com.snowman.project.service.todo.exceptions

import com.snowman.project.config.exceptions.ApiException
import com.snowman.project.config.exceptions.ErrorCode

class CannotAddTodoException : ApiException(ErrorCode.CANNOT_ADD_TODO) {
}