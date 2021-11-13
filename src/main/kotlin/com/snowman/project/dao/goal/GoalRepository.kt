package com.snowman.project.dao.goal

import com.snowman.project.model.goal.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoalRepository : JpaRepository<Goal, Long>, GoalRepositoryCustom {
}