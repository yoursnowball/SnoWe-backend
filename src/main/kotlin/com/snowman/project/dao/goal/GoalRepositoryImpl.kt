package com.snowman.project.dao.goal

import com.querydsl.core.QueryResults
import com.querydsl.core.Tuple
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.model.goal.entity.QGoal.goal
import com.snowman.project.model.todo.entity.QTodo.todo
import com.snowman.project.model.user.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class GoalRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : GoalRepositoryCustom {

    override fun getBestDailyGoalsByDates(user: User): QueryResults<Tuple> {
        return queryFactory.select(
            goal, todo
        ).from(goal).join(todo)
            .groupBy(goal, todo.createAt)
            .where(goal.user.eq(user), dateBetween())
            .fetchResults()
    }

    private fun dateBetween(): BooleanExpression {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1).atTime(0, 0, 0)
        val end = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59, 59)

        return todo.createAt.between(start, end)
    }
}