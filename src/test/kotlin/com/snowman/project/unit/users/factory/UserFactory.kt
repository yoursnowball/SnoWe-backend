package com.snowman.project.unit.users.factory

import com.snowman.project.users.model.entity.User
import java.time.LocalDateTime

class UserFactory {

    fun getContentsOwnedUser(): User {
        return User(
            id = 1L,
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit",
            createdAt = LocalDateTime.now()
        )
    }

    fun getNonOwnedUser(): User {
        return User(
            id = 2L,
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit",
            createdAt = LocalDateTime.now()
        )
    }

    fun getFcmTokenIsNotNullUser(): User {
        return User(
            id = 1L,
            fcmToken = "NotNull",
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit",
            createdAt = LocalDateTime.now()
        )
    }

    fun getFcmTokenIsNullUser(): User {
        return User(
            id = 1L,
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit",
            createdAt = LocalDateTime.now()
        )
    }
}