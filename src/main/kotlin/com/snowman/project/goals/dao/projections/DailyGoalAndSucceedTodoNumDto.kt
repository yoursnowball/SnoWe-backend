package com.snowman.project.goals.dao.projections

import com.querydsl.core.annotations.QueryProjection
import com.snowman.project.goals.domain.enums.CharacterType
import java.time.LocalDateTime

data class DailyGoalAndSucceedTodoNumDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val objective: String,
    val level: Int,
    val type: CharacterType,
    val date: String,
    val succeedTodoCount: Int,
    val levelTodoCount: Int,
    val createdAt: LocalDateTime
) {
}