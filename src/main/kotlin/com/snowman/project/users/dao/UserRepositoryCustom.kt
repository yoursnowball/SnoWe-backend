package com.snowman.project.users.dao

import com.snowman.project.users.model.entity.User
import java.time.LocalDate

interface UserRepositoryCustom {
    fun findUserNotWriteTomorrowTodoYet(tomorrow: LocalDate): List<User>
    fun findUserNotCompleteTodayTodoYet(): List<User>
}