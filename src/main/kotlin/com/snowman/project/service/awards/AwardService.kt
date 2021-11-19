package com.snowman.project.service.awards

import com.snowman.project.config.exceptions.common.DeletedContentException
import com.snowman.project.config.exceptions.common.NotYourContentException
import com.snowman.project.dao.awards.AwardRepository
import com.snowman.project.dao.goal.GoalRepository
import com.snowman.project.dao.todo.TodoRepository
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.awards.dto.AwardInfoDto
import com.snowman.project.model.awards.entity.Awards
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
        return awardRepository.findByUser(user).map { AwardInfoDto(it) }
    }

    @Transactional(readOnly = true)
    fun getAwardsByRanking(page: Int): Page<AwardInfoDto> {
        return awardRepository.findAll(PageUtils.of(page, Sort.by("total_todo_count").descending()))
            .map { AwardInfoDto(it) }
    }

    fun saveAwards(userId: Long, goalId: Long): AwardInfoDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()

        if (goal.user != user)
            throw NotYourContentException()
        if (goal.deleted)
            throw DeletedContentException()
        if (goal.canMoveToAwards())
            throw CannotMoveToAwardsException()

        val award = awardRepository.save(
            Awards(
                goal = goal,
                totalTodoCount = todoRepository.countAllByGoal(goal),
                user = user
            )
        )

        return AwardInfoDto(award)
    }

}