package com.snowman.project.dao.user

import com.snowman.project.model.user.entity.User
import java.time.LocalDate

interface UserRepositoryCustom {
    fun findUserNotWriteTodoYet(tomorrow: LocalDate): List<User>
}