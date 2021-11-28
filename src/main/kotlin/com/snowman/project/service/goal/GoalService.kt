package com.snowman.project.service.goal

import com.snowman.project.config.exceptions.common.DateRangeException
import com.snowman.project.config.exceptions.common.DeletedContentException
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.goal.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.dao.todo.projections.TodoWIthGoalIdDto
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.goal.dto.DailyGoalInfoDto
import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.todo.dto.TodoInfoDto
import com.snowman.project.service.goal.exceptions.AlreadyMaximumGoalsException
import com.snowman.project.service.goal.exceptions.CannotDeleteGoalException
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
    /**
     * 삭제, 명예의전당에간 목표도 보여주는 히스토리성 데이터
     */
    @Transactional(readOnly = true)
    fun getMyDailyGoalsHistory(userId: Long, date: LocalDate): List<DailyGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val todos: Map<Long, List<TodoWIthGoalIdDto>> = todoService.getTodosByDate(user, date)
        val goalWithTodos: MutableList<DailyGoalInfoDto> = mutableListOf()

        for (goalId in todos.keys) {
            goalRepository.findByIdOrNull(goalId)?.let { goal ->
                val dailyTodos = todos[goalId]!!
                goalWithTodos.add(
                    DailyGoalInfoDto(
                        id = goal.id!!,
                        name = goal.name,
                        objective = goal.objective,
                        createdAt = goal.createdAt!!,
                        level = goal.level,
                        type = goal.characterType,
                        todaySucceedTodoCount = dailyTodos.filter { it.finishedAt != null }.count(),
                        todayTotalTodoCount = dailyTodos.size,
                        todos = dailyTodos.map { todo ->
                            TodoInfoDto(
                                id = todo.todoId,
                                name = todo.name,
                                succeed = todo.succeed,
                                createdAt = todo.createdAt,
                                finishedAt = todo.finishedAt,
                                todoDate = todo.todoDate
                            )
                        }
                    )
                )
            }
        }
        return goalWithTodos
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

        return DetailGoalInfoDto(goal, todoService.getTodosByGoalAndDate(user, goal, date))
    }

    /**
     * 명예의전당에 가거나 삭제되지 않은 오늘의 현재 진행중인 목표와 투두리스트 리턴
     */
    @Transactional(readOnly = true)
    fun getMyTodayActiveGoals(userId: Long): List<DetailGoalInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return goalRepository.findAllByUserAndDeletedIsFalseAndAwardedIsFalse(user)
            .map { DetailGoalInfoDto(it, todoService.getTodosByGoalAndDate(user, it, LocalDate.now())) }
    }

    /**
     * 목표 저장
     */
    fun saveGoal(userId: Long, name: String, objective: String, type: CharacterType): DetailGoalInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val myOwnGoals = goalRepository.countAllByUserAndDeletedIsFalse(user)

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