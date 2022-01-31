package com.snowman.project.awards.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.awards.dao.projections.AwardInfoDto
import com.snowman.project.awards.dao.projections.QAwardInfoDto
import com.snowman.project.awards.domain.entity.QAward.award
import com.snowman.project.goals.domain.entity.QGoal.goal
import com.snowman.project.users.model.entity.QUser.user

import com.snowman.project.users.model.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class AwardRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : AwardRepositoryCustom {

    override fun getAwardsByRank(pageable: Pageable): Page<AwardInfoDto> {
        val queryResult = queryFactory.select(
            QAwardInfoDto(
                award.id!!,
                goal.name,
                user.nickName,
                goal.objective,
                award.createdAt!!,
                goal.createdAt!!,
                goal.succeedTodoCount,
                award.totalTodoCount,
                goal.characterType,
                goal.level
            )
        ).from(award)
            .join(goal).on(award.goal.eq(goal))
            .join(user).on(award.user.eq(user))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()

        return PageImpl(queryResult.results, pageable, queryResult.total)
    }

    override fun findByUser(owner: User): List<AwardInfoDto> {
        return queryFactory.select(
            QAwardInfoDto(
                award.id!!,
                goal.name,
                user.nickName,
                goal.objective,
                award.createdAt!!,
                goal.createdAt!!,
                goal.succeedTodoCount,
                award.totalTodoCount,
                goal.characterType,
                goal.level
            )
        ).from(award)
            .join(goal).on(award.goal.eq(goal))
            .join(user).on(goal.user.eq(user))
            .where(user.eq(owner))
            .fetch()
    }

}