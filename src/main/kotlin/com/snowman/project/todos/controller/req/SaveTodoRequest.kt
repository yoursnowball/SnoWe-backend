package com.snowman.project.todos.controller.req

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.NotEmpty

@ApiModel("투두 저장 요청")
data class SaveTodoRequest(
    @ApiModelProperty("투두")
    val todo: String,

    @field:NotEmpty
    @ApiModelProperty("투두 시점")
    val date: LocalDate
) {
}