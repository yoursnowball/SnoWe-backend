package com.snowman.project.controller.todo.res

import com.snowman.project.model.todo.dto.TodoInfoDto
import java.time.LocalDateTime

data class GetTodoResponse(
    val id: Long,
    val name: String,
    val succeed: Boolean,
    val createdAt: LocalDateTime,
    val finishedAt: LocalDateTime?
) {
    constructor(dto: TodoInfoDto) : this(dto.id, dto.name, dto.succeed, dto.createdAt, dto.finishedAt)
}