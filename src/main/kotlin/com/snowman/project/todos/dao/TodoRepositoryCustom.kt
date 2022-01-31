package com.snowman.project.todos.dao

import com.snowman.project.todos.dao.projections.TodoWIthGoalIdDto
import com.snowman.project.users.model.entity.User
import java.time.LocalDate

interface TodoRepositoryCustom {
    fun getTodosWithGoalByUserAndDate(user: User, date: LocalDate): List<TodoWIthGoalIdDto>
}