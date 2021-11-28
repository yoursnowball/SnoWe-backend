package com.snowman.project.dao.todo

import com.snowman.project.dao.todo.projections.TodoWIthGoalIdDto
import com.snowman.project.model.user.entity.User
import java.time.LocalDate

interface TodoRepositoryCustom {
    fun getTodosWithGoalByUserAndDate(user: User, date: LocalDate): List<TodoWIthGoalIdDto>
}