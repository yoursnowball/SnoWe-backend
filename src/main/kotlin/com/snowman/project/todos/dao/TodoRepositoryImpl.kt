package com.snowman.project.todos.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.goals.domain.entity.QGoal.goal
import com.snowman.project.todos.dao.projections.QTodoWIthGoalIdDto
import com.snowman.project.todos.dao.projections.TodoWIthGoalIdDto
import com.snowman.project.todos.domain.entity.QTodo.todo

import com.snowman.project.users.model.entity.User
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