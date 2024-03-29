package com.snowman.project.todos.domain.dto

import com.snowman.project.todos.domain.entity.Todo
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.LocalDateTime

@ApiModel("투두 정보")
data class TodoInfoDto(
    @ApiModelProperty("투두 식별자")
    val id: Long,
    @ApiModelProperty("이름")
    val name: String,
    @ApiModelProperty("투두 성공 여부")
    val succeed: Boolean,
    @ApiModelProperty("투두 실행 날짜(yyyy-MM-dd)")
    val todoDate: LocalDate,
    @ApiModelProperty("투두 생성 시간 (yyyy-MM-dd HH:mm:ss)")
    val createdAt: LocalDateTime,
    @ApiModelProperty("투두 완료 시간 (yyyy-MM-dd HH:mm:ss)")
    val finishedAt: LocalDateTime?
) {
    constructor(todo: Todo) : this(todo.id!!, todo.name, todo.succeed, todo.todoDate, todo.createdAt!!, todo.finishedAt)
}