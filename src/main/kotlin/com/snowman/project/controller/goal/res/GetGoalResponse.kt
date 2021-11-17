package com.snowman.project.controller.goal.res

import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.todo.dto.TodoInfoDto
import java.time.LocalDateTime

class GetGoalResponse(
        val id: Long,
        val name: String,
        val createdAt: LocalDateTime,
        val level: Int,
        val succeedTodoCount: Int,
        val levelTodoCount: Int,
        val type: CharacterType,
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