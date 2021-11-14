package com.snowman.project.model.goal.dto

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import java.time.LocalDateTime

class SimpleGoalInfoDto(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val level: Int,
    val succeedTotalCount: Int,
    val type: CharacterType
) {
    constructor(goal: Goal) : this(
        goal.id!!,
        goal.name,
        goal.createAt!!,
        goal.level,
        goal.succeedTodoCount,
        goal.characterType
    )
}