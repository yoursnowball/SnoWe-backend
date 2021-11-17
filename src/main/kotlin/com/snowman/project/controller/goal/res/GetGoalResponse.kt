package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.todo.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("목표 상세")
class GetGoalResponse(
        @ApiModelProperty("목표 식별자")
        val id: Long,
        @ApiModelProperty("목표 이름")
        val name: String,
        @ApiModelProperty("목표 생성날짜 (yyyy-MM-dd HH:mm:ss)")
        val createdAt: LocalDateTime,
        @ApiModelProperty("레벨")
        val level: Int,
        @ApiModelProperty("성공한 투두 개수")
        val succeedTodoCount: Int,
        @ApiModelProperty("현제 레벨에서 성공한 투두 개수")
        val levelTodoCount: Int,
        @ApiModelProperty("목표 캐릭터 종류")
        val type: CharacterType,
        @ApiModelProperty("해당 날짜 투두 리스트")
        val todos: List<TodoInfoDto>
) {
    constructor(dto: DetailGoalInfoDto) : this(
            dto.id,
            dto.name,
            dto.createdAt,
            dto.level,
            dto.succeedTodoCount,
            dto.levelTodoCount,
            dto.type,
            dto.todos
    )
}