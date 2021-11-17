package com.snowman.project.model.goal.dto

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("목표 요약")
class SimpleGoalInfoDto(
        @ApiModelProperty("목표 식별자")
        val id: Long,
        @ApiModelProperty("목표 이름")
        val name: String,
        @ApiModelProperty("목표 생성일")
        val createdAt: LocalDateTime,
        @ApiModelProperty("레벨")
        val level: Int,
        @ApiModelProperty("성공한 투두 개수")
        val succeedTodoCount: Int,
        @ApiModelProperty("현제 레벨에서 성공한 투두 개수")
        val levelTodoCount: Int,
        @ApiModelProperty("목표 캐릭터 종류")
        val type: CharacterType
) {
    constructor(goal: Goal) : this(
            goal.id!!,
            goal.name,
            goal.createAt!!,
            goal.level,
            goal.succeedTodoCount,
            goal.levelTodoCount,
            goal.characterType
    )
}