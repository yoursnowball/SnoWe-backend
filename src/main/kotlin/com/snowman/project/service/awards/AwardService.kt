package com.snowman.project.service.awards

import com.snowman.project.config.exceptions.common.DeletedContentException
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.awards.AwardRepository
import com.snowman.project.dao.awards.projections.AwardInfoDto
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.awards.entity.Award
import com.snowman.project.service.awards.exceptions.CannotMoveToAwardsException
import com.snowman.project.service.goal.exceptions.GoalNotExistException
import com.snowman.project.service.user.exceptions.UserNotExistException
import com.snowman.project.utils.page.PageUtils
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
    private val todoRepository: TodoRepository
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

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted)
            throw DeletedContentException()
        if (!goal.canMoveToAwards())
            throw CannotMoveToAwardsException()

        goal.moveToAward()
        val award = awardRepository.save(
            Award(
                goal = goal,
                totalTodoCount = todoRepository.countAllByGoal(goal),
                user = user
            )
        )

        return AwardInfoDto(award)
    }

}