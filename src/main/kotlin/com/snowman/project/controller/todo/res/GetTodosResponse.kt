package com.snowman.project.controller.todo.res

import com.snowman.project.model.todo.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("투두 리스트")
data class GetTodosResponse(
        @ApiModelProperty("투두 리스트")
        val todos: List<TodoInfoDto>
)