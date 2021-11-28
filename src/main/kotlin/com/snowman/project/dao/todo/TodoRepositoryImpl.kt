package com.snowman.project.dao.todo

import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.dao.todo.projections.QTodoWIthGoalIdDto
import com.snowman.project.dao.todo.projections.TodoWIthGoalIdDto
import com.snowman.project.model.goal.entity.QGoal.goal
import com.snowman.project.model.todo.entity.QTodo.todo
import com.snowman.project.model.user.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TodoRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : TodoRepositoryCustom {
    override fun getTodosWithGoalByUserAndDate(user: User, date: LocalDate): List<TodoWIthGoalIdDto> {
        return queryFactory.select(
            QTodoWIthGoalIdDto(
                goal.id,
                todo.id,
                todo.name,
                todo.succeed,
                todo.createdAt,
                todo.finishedAt,
                todo.todoDate
            )
        ).from(todo).join(goal).on(todo.goal.eq(goal)).fetchJoin()
            .where(todo.user.eq(user), todo.todoDate.eq(date))
            .fetch()


    }
}