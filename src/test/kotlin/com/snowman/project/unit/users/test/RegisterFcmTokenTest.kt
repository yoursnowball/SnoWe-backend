package com.snowman.project.unit.users.test

import com.snowman.project.unit.users.UserTestBase
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.BDDMockito.given
import java.util.Optional

class RegisterFcmTokenTest : UserTestBase() {

    @Test
    @DisplayName("Delete FcmToken Test")
    fun deleteFcmTokenSuccessTest() {
        val targetUser = getFcmTokenIsNullUser()
        given(userRepository.findById(1L)).willReturn(Optional.of(targetUser))
        assertTrue(targetUser.fcmToken == null)
        assertDoesNotThrow { userService.registerFcmToken(1L, "newToken") }
        assertTrue(targetUser.fcmToken == "newToken")
    }
}