package com.snowman.project.controller.todo.req

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty

@ApiModel("투두리스트 저장 요청")
data class SaveTodoRequest(
        @ApiModelProperty("투두 리스트")
        @field:NotEmpty
        val todos: List<String>
) {
}