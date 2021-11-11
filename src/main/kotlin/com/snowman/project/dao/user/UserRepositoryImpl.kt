package com.snowman.project.dao.user

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {
}