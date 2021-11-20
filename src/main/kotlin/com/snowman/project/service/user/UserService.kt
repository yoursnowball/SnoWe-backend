package com.snowman.project.service.user

import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.user.dto.DetailUserInfoDto
import com.snowman.project.model.user.dto.SimpleUserInfoDto
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(val userRepository: UserRepository) {

    fun updateUserInfo(
        id: Long,
        dto: SimpleUserInfoDto
    ): SimpleUserInfoDto {
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotExistException()
        user.update(dto.nickName, dto.alarmTime)

        return SimpleUserInfoDto(user.nickName, user.alarmTime!!)
    }

    @Transactional(readOnly = true)
    fun getUserInfo(id: Long): DetailUserInfoDto {
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotExistException()
        return DetailUserInfoDto(user.nickName, user.alarmTime, user.createdAt!!)
    }

    fun registerFcmToken(userId: Long, token: String): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return user.registerFcmToken(token)
    }
}