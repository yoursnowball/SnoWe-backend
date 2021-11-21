package com.snowman.project.dao.awards

import com.snowman.project.dao.awards.projections.AwardInfoDto
import com.snowman.project.model.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface AwardRepositoryCustom {
    fun getAwardsByRank(pageable: Pageable): Page<AwardInfoDto>
    fun findByUser(user: User): List<AwardInfoDto>
}