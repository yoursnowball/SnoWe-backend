package com.snowman.project.goals.dao

import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.users.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface GoalRepository : JpaRepository<Goal, Long>, GoalRepositoryCustom {
    fun findAllByUserAndDeletedIsFalseAndAwardedIsFalse(user: User): List<Goal>
    fun countAllByUserAndDeletedIsFalseAndAwardedIsFalse(user: User): Int
}