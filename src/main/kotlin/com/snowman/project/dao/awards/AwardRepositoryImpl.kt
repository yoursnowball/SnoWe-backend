package com.snowman.project.dao.awards

import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.dao.awards.projections.AwardInfoDto
import com.snowman.project.dao.awards.projections.QAwardInfoDto
import com.snowman.project.model.awards.entity.QAward.award
import com.snowman.project.model.goal.entity.QGoal.goal
import com.snowman.project.model.user.entity.QUser.user
import com.snowman.project.model.user.entity.User
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