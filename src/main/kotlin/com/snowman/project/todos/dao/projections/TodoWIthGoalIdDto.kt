package com.snowman.project.todos.dao.projections

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate
import java.time.LocalDateTime

data class TodoWIthGoalIdDto @QueryProjection constructor(
    val goalId: Long,
    val todoId: Long,
    val name: String,
    val succeed: Boolean,
    val createdAt: LocalDateTime,
    val finishedAt: LocalDateTime?,
    val todoDate: LocalDate

)
