package com.snowman.project.awards.dao

import com.snowman.project.awards.dao.projections.AwardInfoDto
import com.snowman.project.users.model.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface AwardRepositoryCustom {
    fun getAwardsByRank(pageable: Pageable): Page<AwardInfoDto>
    fun findByUser(user: User): List<AwardInfoDto>
}