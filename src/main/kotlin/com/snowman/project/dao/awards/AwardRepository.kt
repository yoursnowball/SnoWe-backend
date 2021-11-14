package com.snowman.project.dao.awards

import com.snowman.project.model.awards.entity.Awards
import com.snowman.project.model.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AwardRepository : JpaRepository<Awards, Long>, AwardRepositoryCustom {
    fun findByUser(user: User): List<Awards>
}