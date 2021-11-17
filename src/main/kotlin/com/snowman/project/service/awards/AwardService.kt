package com.snowman.project.service.awards

import com.snowman.project.config.exceptions.ErrorCode
import com.snowman.project.dao.awards.AwardRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.awards.dto.AwardInfoDto
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AwardService(
    val awardRepository: AwardRepository,
    val userRepository: UserRepository
) {

    fun getMyAwards(userId: Long): List<AwardInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return awardRepository.findByUser(user).map { AwardInfoDto(it) }
    }
}