package com.snowman.project.todos.dao

import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.todos.domain.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TodoRepository : JpaRepository<Todo, Long>, TodoRepositoryCustom {
    fun findAllByGoalIsInAndTodoDate(
        goals: List<Goal>,
        todoDate: LocalDate
    ): List<Todo>

    fun findAllByGoalAndTodoDate(goal: Goal, todoDate: LocalDate): List<Todo>
    fun countAllByGoal(goal: Goal): Int
    fun countAllByGoalAndTodoDateAndSucceedIsFalse(goal: Goal, date: LocalDate): Int
}