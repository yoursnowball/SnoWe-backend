package com.snowman.project.dao.user

import com.snowman.project.model.user.entity.User
import java.time.LocalDate

interface UserRepositoryCustom {
    fun findUserNotWriteTomorrowTodoYet(tomorrow: LocalDate): List<User>
    fun findUserNotCompleteTodayTodoYet(): List<User>
}