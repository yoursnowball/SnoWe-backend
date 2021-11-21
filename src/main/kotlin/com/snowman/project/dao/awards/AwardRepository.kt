package com.snowman.project.dao.awards

import com.snowman.project.model.awards.entity.Award
import com.snowman.project.model.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AwardRepository : JpaRepository<Award, Long>, AwardRepositoryCustom {
}