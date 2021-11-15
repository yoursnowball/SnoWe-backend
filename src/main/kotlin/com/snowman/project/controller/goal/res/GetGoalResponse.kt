package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.enums.CharacterType
import java.time.LocalDateTime

class GetGoalResponse(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val level: Int,
    val succeedTodoCount: Int,
    val levelTodoCount: Int,
    val type: CharacterType
) {
    constructor(dto: SimpleGoalInfoDto) : this(
        dto.id,
        dto.name,
        dto.createdAt,
        dto.level,
        dto.succeedTodoCount,
        dto.levelTodoCount,
        dto.type
    )
}