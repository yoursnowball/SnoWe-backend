package com.snowman.project.goals.domain.dto

import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.domain.enums.CharacterType
import com.snowman.project.todos.domain.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("목표 상세")
class DetailGoalInfoDto(
    @ApiModelProperty("목표 식별자")
    val id: Long,
    @ApiModelProperty("목표 이름")
    val name: String,
    @ApiModelProperty("목표 설정")
    val objective: String,
    @ApiModelProperty("목표 생성일")
    val createdAt: LocalDateTime,
    @ApiModelProperty("레벨")
    val level: Int,
    @ApiModelProperty("성공한 투두 개수")
    val succeedTodoCount: Int,
    @ApiModelProperty("현제 레벨에서 성공한 투두 개수")
    val levelTodoCount: Int,
    @ApiModelProperty("목표 캐릭터 종류")
    val type: CharacterType,
    @ApiModelProperty("투두 리스트")
    val todos: List<TodoInfoDto>
) {
    constructor(goal: Goal, todos: List<TodoInfoDto>) : this(
        goal.id!!,
        goal.name,
        goal.objective,
        goal.createdAt!!,
        goal.level,
        goal.succeedTodoCount,
        goal.levelTodoCount,
        goal.characterType,
        todos
    )
}