package com.snowman.project.service.user

import com.snowman.project.dao.push.PushRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.push.dto.PushHistoryDto
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.model.user.dto.DetailUserInfoDto
import com.snowman.project.service.push.PushService
import com.snowman.project.service.user.exceptions.UserNotExistException
import com.snowman.project.utils.page.PageUtils
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    val userRepository: UserRepository,
    val pushRepository: PushRepository,
    val pushService: PushService
) {
    @Transactional(readOnly = true)
    fun getUserInfo(id: Long): DetailUserInfoDto {
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotExistException()
        return DetailUserInfoDto(user.nickName, user.createdAt!!)
    }

    fun registerFcmToken(userId: Long, token: String): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return user.registerFcmToken(token)
    }

    @Transactional(readOnly = true)
    fun getAlarmHistory(userId: Long, page: Int): Page<PushHistoryDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return pushRepository.findAllByUserOrderByCreatedAtDesc(user, PageUtils.of(page)).map { PushHistoryDto(it) }
    }

    fun deleteToken(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        user.deleteFcmToken()
    }

    fun testPush(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        pushService.sendPushMessages(listOf(user), PushType.CHEERUP)

    }
}