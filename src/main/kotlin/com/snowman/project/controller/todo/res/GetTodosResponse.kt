package com.snowman.project.controller.todo.res

import com.snowman.project.model.todo.dto.TodoInfoDto

data class GetTodosResponse(
    val todos: List<TodoInfoDto>
)