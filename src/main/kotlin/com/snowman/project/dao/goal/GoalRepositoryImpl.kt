package com.snowman.project.dao.goal

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.dao.goal.projections.DailyGoalAndSucceedTodoNumDto
import com.snowman.project.dao.goal.projections.QDailyGoalAndSucceedTodoNumDto
import com.snowman.project.model.goal.entity.QGoal.goal
import com.snowman.project.model.todo.entity.QTodo.todo
import com.snowman.project.model.user.entity.User
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
            "DATE_FORMAT({0}, {1})", todo.createdAt, "%Y-%m-%d"
        )
        return queryFactory
            .select(
                QDailyGoalAndSucceedTodoNumDto(
                    goal.id!!,
                    goal.name,
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
            .where(dateBetween(startDate, endDate))
            .groupBy(
                date,
                goal.id
            )
            .orderBy(date.asc())
            .fetch()

    }

    private fun dateBetween(startDate: LocalDate, endDate: LocalDate): BooleanExpression {
        val startDateTime = startDate.atTime(0, 0, 0, 0)
        val endDateTime = endDate.atTime(23, 59, 59, 59)

        return todo.createdAt.between(startDateTime, endDateTime)
    }
}