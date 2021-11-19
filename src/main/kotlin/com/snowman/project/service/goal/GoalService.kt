package com.snowman.project.service.goal

import com.snowman.project.config.exceptions.common.DateRangeException
import com.snowman.project.config.exceptions.common.DeletedContentException
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.goal.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.service.goal.exceptions.GoalNotExistException
import com.snowman.project.service.todo.TodoService
import com.snowman.project.service.user.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class GoalService(
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val todoService: TodoService
) {

    @Transactional(readOnly = true)
    fun getBestDailyGoalsByDates(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Map<String, SimpleGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val dailyMap: MutableMap<String, SimpleGoalInfoDto> = mutableMapOf()

        if (startDate.isAfter(endDate))
            throw DateRangeException()

        val totalDailyMap: Map<String, List<DailyGoalAndSucceedTodoNumDto>> =
            goalRepository.getDailyGoalsWithSucceedTodoCountByDateBetween(user, startDate, endDate)
                .groupBy { it.date }

        for (dailyInfo in totalDailyMap)
            dailyMap[dailyInfo.key] = SimpleGoalInfoDto(dailyInfo.value.maxByOrNull { it.succeedTodoCount }!!)

        return dailyMap
    }

    @Transactional(readOnly = true)
    fun getMyGoal(userId: Long, goalId: Long, date: LocalDate): DetailGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted)
            throw DeletedContentException()

        return DetailGoalInfoDto(goal, todoService.getTodos(user, goal, date))
    }

    @Transactional(readOnly = true)
    fun getMyGoals(userId: Long): List<SimpleGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return goalRepository.findAllByUserAndDeletedIsFalse(user).map { SimpleGoalInfoDto(it) }
    }

    fun saveGoal(userId: Long, name: String, objective: String, type: CharacterType): DetailGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.save(
            Goal(
                name = name,
                objective = objective,
                characterType = type,
                user = user
            )
        )
        return DetailGoalInfoDto(goal, listOf())
    }

    fun deleteGoal(userId: Long, goalId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user != user)
            throw NotYourContentException()

        goal.delete()
    }
}