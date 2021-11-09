package com.snowman.project.dao

import com.snowman.project.model.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoalRepository : JpaRepository<Goal, Long> {
}