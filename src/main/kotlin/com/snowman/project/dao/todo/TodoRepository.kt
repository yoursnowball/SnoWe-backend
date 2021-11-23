package com.snowman.project.dao.todo

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface TodoRepository : JpaRepository<Todo, Long>, TodoRepositoryCustom {
    fun findAllByGoalAndTodoDate(
        goal: Goal,
        todoDate: LocalDate
    ): List<Todo>

    fun countAllByGoal(goal: Goal): Int
    fun countAllByGoalAndTodoDateAndSucceedIsFalse(goal: Goal, date: LocalDate): Int
}