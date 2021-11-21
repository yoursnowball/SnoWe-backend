package com.snowman.project.dao.goal

import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface GoalRepository : JpaRepository<Goal, Long>, GoalRepositoryCustom {
    fun findAllByUserAndDeletedIsFalse(user: User): List<Goal>
    fun countAllByUserAndDeletedIsFalse(user: User): Int
}