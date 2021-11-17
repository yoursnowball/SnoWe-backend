package com.snowman.project.model.goal.dto

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.todo.dto.TodoInfoDto
import java.time.LocalDateTime

class DetailGoalInfoDto(
        val id: Long,
        val name: String,
        val createdAt: LocalDateTime,
        val level: Int,
        val succeedTodoCount: Int,
        val levelTodoCount: Int,
        val type: CharacterType,
        val todos: List<TodoInfoDto>
) {
    constructor(goal: Goal, todos: List<TodoInfoDto>) : this(
            goal.id!!,
            goal.name,
            goal.createAt!!,
            goal.level,
            goal.succeedTodoCount,
            goal.levelTodoCount,
            goal.characterType,
            todos
    )
}