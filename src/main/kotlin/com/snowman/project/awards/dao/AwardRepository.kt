package com.snowman.project.awards.dao

import com.snowman.project.awards.domain.entity.Award
import org.springframework.data.jpa.repository.JpaRepository

interface AwardRepository : JpaRepository<Award, Long>, AwardRepositoryCustom {
}