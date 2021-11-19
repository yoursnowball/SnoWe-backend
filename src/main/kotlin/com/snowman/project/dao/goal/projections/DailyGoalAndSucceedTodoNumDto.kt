package com.snowman.project.dao.goal.projections

import com.querydsl.core.annotations.QueryProjection
import com.snowman.project.model.goal.enums.CharacterType
import java.time.LocalDateTime

data class DailyGoalAndSucceedTodoNumDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val level: Int,
    val type: CharacterType,
    val date: String,
    val succeedTodoCount: Int,
    val levelTodoCount: Int,
    val createdAt: LocalDateTime
) {
}