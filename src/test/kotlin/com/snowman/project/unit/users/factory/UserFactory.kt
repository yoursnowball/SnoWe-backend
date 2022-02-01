package com.snowman.project.unit.users.factory

import com.snowman.project.users.model.entity.User

class UserFactory {

    fun getContentsOwnedUser(): User {
        return User(
            id = 1L,
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit"
        )
    }

    fun getNonOwnedUser(): User {
        return User(
            id = 2L,
            userName = "testAccount",
            password = "encryptedPassword",
            nickName = "TestUnit"
        )
    }
}