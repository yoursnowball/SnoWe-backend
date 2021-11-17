package com.snowman.project.controller.todo.req

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty

@ApiModel("투두리스트 수정 요청")
data class UpdateTodoRequest(
        @ApiModelProperty("투두 이름")
        @field:NotEmpty
        val name: String,
        @ApiModelProperty("투두 성공 여부")
        @field:NotEmpty
        val succeed: Boolean
) {
}