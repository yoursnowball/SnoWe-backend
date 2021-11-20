package com.snowman.project.dao.user

import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.model.todo.entity.QTodo.todo
import com.snowman.project.model.user.entity.QUser.user
import com.snowman.project.model.user.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class UserRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    override fun findUserNotWriteTodoYet(tomorrow: LocalDate): List<User> {
        return queryFactory.select(
            user
        )
            .from(user)
            .join(todo).on(todo.user.eq(user), todo.todoDate.eq(tomorrow))
            .fetch()
    }
}