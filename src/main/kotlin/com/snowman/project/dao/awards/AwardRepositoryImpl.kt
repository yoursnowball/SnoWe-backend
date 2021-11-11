package com.snowman.project.dao.awards

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AwardRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : AwardRepositoryCustom {
}