package com.snowman.project.model.todo.dto

import com.snowman.project.model.todo.entity.Todo
import java.time.LocalDateTime

data class TodoInfoDto(
    val id: Long,
    val name: String,
    val succeed: Boolean,
    val createdAt: LocalDateTime,
    val finishedAt: LocalDateTime?
) {
    constructor(todo: Todo) : this(todo.id!!, todo.name, todo.succeed, todo.createAt!!, todo.finishedAt)
}