package com.snowman.project.awards.service

import com.snowman.project.awards.dao.AwardRepository
import com.snowman.project.awards.dao.projections.AwardInfoDto
import com.snowman.project.awards.domain.entity.Award
import com.snowman.project.awards.service.exceptions.CannotMoveToAwardsException
import com.snowman.project.common.exception.DeletedContentException
import com.snowman.project.common.exception.NotYourContentException
import com.snowman.project.common.utils.page.PageUtils
import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.service.exceptions.GoalNotExistException
import com.snowman.project.todos.dao.TodoRepository
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.service.exceptions.UserNotExistException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AwardService(
    private val awardRepository: AwardRepository,
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
    private val todoRepository: TodoRepository,
    private val publisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getMyAwards(userId: Long): List<AwardInfoDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        return awardRepository.findByUser(user)
    }

    @Transactional(readOnly = true)
    fun getAwardsByRanking(page: Int): Page<AwardInfoDto> {
        val pageable = PageUtils.of(page, Sort.by("succeed_todo_count"))
        return awardRepository.getAwardsByRank(pageable)
    }

    fun saveAwards(userId: Long, goalId: Long): AwardInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user.id != user.id)
            throw NotYourContentException()
        if (goal.deleted)
            throw DeletedContentException()
        if (!goal.canMoveToAwards())
            throw CannotMoveToAwardsException()

        val award = awardRepository.save(
            Award(
                goal = goal,
                totalTodoCount = todoRepository.countAllByGoal(goal),
                user = user
            ).publishCreateEvent()
        )
        award.pollAllEvent().forEach { publisher.publishEvent(it) }
        return AwardInfoDto(award)
    }

}