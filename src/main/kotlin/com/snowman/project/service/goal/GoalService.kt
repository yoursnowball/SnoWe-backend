package com.snowman.project.service.goal

import com.snowman.project.config.exceptions.ErrorCode
import com.snowman.project.config.exceptions.common.DeletedContentException
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.service.goal.exceptions.GoalNotExistException
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GoalService(
    val goalRepository: GoalRepository,
    val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getMyGoal(userId: Long, goalId: Long): SimpleGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException(ErrorCode.GOAL_NOT_EXIST)

        if (goal.user != user)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)
        if (goal.isDeleted)
            throw DeletedContentException(ErrorCode.DELETED_CONTENT)

        return SimpleGoalInfoDto(goal)
    }

    @Transactional(readOnly = true)
    fun getMyGoals(userId: Long): List<SimpleGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        return goalRepository.findAllByUserAAndDeletedIsFalse(user).map { SimpleGoalInfoDto(it) }
    }

    fun saveGoal(userId: Long, name: String, type: CharacterType): SimpleGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        val goal = goalRepository.save(
            Goal(
                name = name,
                characterType = type,
                user = user
            )
        )
        return SimpleGoalInfoDto(goal)
    }

    fun deleteGoal(userId: Long, goalId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException(ErrorCode.USER_NOT_EXIST)
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException(ErrorCode.GOAL_NOT_EXIST)

        if (goal.user != user)
            throw NotYourContentException(ErrorCode.NOT_YOUR_CONTENT)

        goal.delete()
    }
}