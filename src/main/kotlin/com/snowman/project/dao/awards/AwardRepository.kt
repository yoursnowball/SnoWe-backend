package com.snowman.project.dao.awards

import com.snowman.project.model.awards.Awards
import org.springframework.data.jpa.repository.JpaRepository

interface AwardRepository : JpaRepository<Awards, Long>, AwardRepositoryCustom {
}