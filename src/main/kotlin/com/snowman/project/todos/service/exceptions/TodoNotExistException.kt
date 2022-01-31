package com.snowman.project.todos.service.exceptions

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode

class TodoNotExistException() : ApiException(ErrorCode.TODO_NOT_EXIST) {
}