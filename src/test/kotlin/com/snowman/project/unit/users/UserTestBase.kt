package com.snowman.project.unit.users

import com.snowman.project.notifications.dao.PushRepository
import com.snowman.project.unit.users.factory.UserFactory
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import com.snowman.project.users.service.UserService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
open class UserTestBase {

    private val userFactory: UserFactory = UserFactory()

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var pushRepository: PushRepository

    @InjectMocks
    lateinit var userService: UserService

    fun getFcmTokenIsNotNullUser(): User {
        return userFactory.getFcmTokenIsNotNullUser()
    }

    fun getFcmTokenIsNullUser(): User {
        return userFactory.getFcmTokenIsNullUser()
    }
}