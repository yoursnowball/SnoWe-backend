package com.snowman.project.goals.dao

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.goals.dao.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.goals.dao.projections.QDailyGoalAndSucceedTodoNumDto
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.goals.domain.entity.QGoal.goal
import com.snowman.project.todos.domain.entity.QTodo.todo
import com.snowman.project.users.model.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate


@Repository
class GoalRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : GoalRepositoryCustom {
    
    override fun getDailyGoalsWithSucceedTodoCountByDateBetween(
        user: User,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<DailyGoalAndSucceedTodoNumDto> {
        val date = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})", todo.todoDate, "%Y-%m-%d"
        )
        return queryFactory
            .select(
                QDailyGoalAndSucceedTodoNumDto(
                    goal.id!!,
                    goal.name,
                    goal.objective,
                    goal.level,
                    goal.characterType,
                    date,
                    todo.count().intValue(),
                    goal.levelTodoCount,
                    goal.createdAt!!
                )
            )
            .from(goal)
            .join(todo).on(todo.goal.eq(goal), todo.succeed.isTrue)
            .where(createdAtDateBetween(startDate, endDate), goal.user.eq(user))
            .groupBy(
                date,
                goal.id
            )
            .orderBy(date.asc())
            .fetch()

    }

    override fun getActiveGoalsByDateAndUser(user: User, date: LocalDate): List<Goal> {
        return queryFactory.selectFrom(goal)
            .where((isActiveWhenFinishedAtIsNull(date).or(isActiveWhenFinishedAtIsNotNull(date))), goal.user.eq(user))
            .fetch()
    }

    private fun createdAtDateBetween(startDate: LocalDate, endDate: LocalDate): BooleanExpression {
        val startDateTime = startDate.atStartOfDay()
        val endDateTime = endDate.atTime(23, 59, 59, 59)

        return todo.createdAt.between(startDateTime, endDateTime)
    }

    private fun isActiveWhenFinishedAtIsNull(date: LocalDate): BooleanExpression {
        return goal.finishedAt.isNull.and(goal.createdAt.before(date.atTime(23, 59, 59, 59)))
    }

    private fun isActiveWhenFinishedAtIsNotNull(date: LocalDate): BooleanExpression {
        val createDateTime = date.plusDays(1).atStartOfDay()
        val endDateTime = date.atStartOfDay()
        return goal.finishedAt.isNotNull.and(
            goal.createdAt.before(createDateTime).and(goal.finishedAt.after(endDateTime))
        )
    }
}