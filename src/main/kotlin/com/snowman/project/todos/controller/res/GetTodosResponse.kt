package com.snowman.project.todos.controller.res

import com.snowman.project.todos.domain.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("투두 리스트")
data class GetTodosResponse(
        @ApiModelProperty("투두 리스트")
        val todos: List<TodoInfoDto>
)