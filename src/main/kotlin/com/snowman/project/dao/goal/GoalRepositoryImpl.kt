package com.snowman.project.dao.goal

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class GoalRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : GoalRepositoryCustom {
}