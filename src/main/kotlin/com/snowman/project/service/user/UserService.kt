package com.snowman.project.service.user

import com.snowman.project.config.exceptions.ErrorCode
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.user.dto.DetailUserInfoDto
import com.snowman.project.model.user.dto.SimpleUserInfoDto
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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

    fun getUserInfo(id: Long): DetailUserInfoDto {
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotExistException()
        return DetailUserInfoDto(user.nickName, user.alarmTime, user.createdAt!!)
    }
}