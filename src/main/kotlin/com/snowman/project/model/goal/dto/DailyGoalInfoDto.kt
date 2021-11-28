package com.snowman.project.model.goal.dto

import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.todo.dto.TodoInfoDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

data class DailyGoalInfoDto(
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
    @ApiModelProperty("오늘 성공한 투두 개수")
    val todaySucceedTodoCount: Int,
    @ApiModelProperty("오늘의 투두 개수")
    val todayTotalTodoCount: Int,
    @ApiModelProperty("목표 캐릭터 종류")
    val type: CharacterType,
    @ApiModelProperty("투두 리스트")
    val todos: List<TodoInfoDto>
)
