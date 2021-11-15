package com.snowman.project.controller.todo.req

import javax.validation.constraints.NotEmpty

data class SaveTodoRequest(
    @field:NotEmpty
    val todos: List<String>
) {
}