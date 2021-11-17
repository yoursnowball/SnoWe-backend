package com.snowman.project.controller.todo.res

import com.snowman.project.model.todo.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime


@ApiModel("투두 상세")
data class GetTodoResponse(
        @ApiModelProperty("투두 식별자")
        val id: Long,
        @ApiModelProperty("이름")
        val name: String,
        @ApiModelProperty("투두 성공 여부")
        val succeed: Boolean,
        @ApiModelProperty("투두 생성시간 (yyyy-MM-dd HH:mm:ss)")
        val createdAt: LocalDateTime,
        @ApiModelProperty("투두 완료시간 (yyyy-MM-dd HH:mm:ss)")
        val finishedAt: LocalDateTime?
) {
    constructor(dto: TodoInfoDto) : this(dto.id, dto.name, dto.succeed, dto.createdAt, dto.finishedAt)
}