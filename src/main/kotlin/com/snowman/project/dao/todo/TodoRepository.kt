package com.snowman.project.dao.todo

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long>, TodoRepositoryCustom {
    fun findAllByGoal(goal: Goal) : List<Todo>
}