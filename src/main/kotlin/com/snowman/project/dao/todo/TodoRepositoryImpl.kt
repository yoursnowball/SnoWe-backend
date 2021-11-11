package com.snowman.project.dao.todo

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : TodoRepositoryCustom {
}