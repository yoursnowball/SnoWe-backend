package com.snowman.project.goals.service

import com.snowman.project.common.exception.DateRangeException
import com.snowman.project.common.exception.DeletedContentException
import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.dao.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.goals.domain.dto.DailyGoalInfoDto
import com.snowman.project.goals.domain.dto.DetailGoalInfoDto
import com.snowman.project.goals.domain.dto.SimpleGoalInfoDto
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.domain.enums.CharacterType
import com.snowman.project.goals.service.exceptions.AlreadyMaximumGoalsException
import com.snowman.project.goals.service.exceptions.CannotDeleteGoalException
import com.snowman.project.goals.service.exceptions.GoalNotExistException
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.todos.domain.dto.TodoInfoDto
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.service.exceptions.UserNotExistException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class GoalService(
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository,
    private val publisher: ApplicationEventPublisher
) {
    /**
     * 삭제,명예의전당에 간 목표도 보여주는 히스토리성 데이터
     */
    @Transactional(readOnly = true)
    fun getMyDailyGoalsHistory(userId: Long, date: LocalDate): List<DailyGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val activeGoalsInThatTime = goalRepository.getActiveGoalsByDateAndUser(user, date)

        return activeGoalsInThatTime.map { goal ->
            val todos = todoRepository.findAllByGoalAndTodoDate(goal, date).map { TodoInfoDto(it) }
            DailyGoalInfoDto(
                id = goal.id!!,
                name = goal.name,
                objective = goal.objective,
                createdAt = goal.createdAt!!,
                level = goal.level,
                todaySucceedTodoCount = todos.filter { todo -> todo.succeed }.size,
                todayTotalTodoCount = todos.size,
                type = goal.characterType,
                todos = todos
            )
        }
    }

    /**
     * 입력받은 날짜의 기간동안 매일매일 가장많이 수행한 목표 리턴
     */
    @Transactional(readOnly = true)
    fun getBestDailyGoalByDates(
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

    /**
     * GoalId와 날짜를 통해서 목표 상세와  투두리스트 리턴
     */
    @Transactional(readOnly = true)
    fun getMyGoal(userId: Long, goalId: Long, date: LocalDate): DetailGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted)
            throw DeletedContentException()
        val todos = todoRepository.findAllByGoalAndTodoDate(goal, date).map { TodoInfoDto(it) }
        return DetailGoalInfoDto(goal, todos)
    }

    /**
     * 명예의전당에 가거나 삭제되지 않은 오늘의 현재 진행중인 목표와 투두리스트 리턴
     */
    @Transactional(readOnly = true)
    fun getMyTodayActiveGoals(userId: Long): List<DetailGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return goalRepository.findAllByUserAndDeletedIsFalseAndAwardedIsFalse(user)
            .map { goal ->
                DetailGoalInfoDto(
                    goal,
                    todoRepository.findAllByGoalAndTodoDate(goal, LocalDate.now()).map { todo -> TodoInfoDto(todo) })
            }
    }

    /**
     * 목표 저장
     */
    fun saveGoal(userId: Long, name: String, objective: String, type: CharacterType): DetailGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val myOwnGoals = goalRepository.countAllByUserAndDeletedIsFalseAndAwardedIsFalse(user)

        if (myOwnGoals >= 4)
            throw AlreadyMaximumGoalsException()

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

    fun todoSuccess(goalId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        goal.todoSuccess()
        goal.pollAllEvent().forEach { publisher.publishEvent(it) }

    }

    fun todoFail(goalId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        goal.todoFail()
    }

    fun goalMoveToAward(goalId: Long) {
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        goal.moveToAward()
    }

    /**
     * 목표 삭제(flag)
     */
    fun deleteGoal(userId: Long, goalId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.awarded)
            throw CannotDeleteGoalException()

        goal.delete()
    }
}