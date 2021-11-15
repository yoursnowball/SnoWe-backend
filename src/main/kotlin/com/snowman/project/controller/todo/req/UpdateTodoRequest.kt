package com.snowman.project.controller.todo.req

import javax.validation.constraints.NotEmpty

data class UpdateTodoRequest(
    @field:NotEmpty
    val name: String,
    @field:NotEmpty
    val succeed: Boolean
) {
}