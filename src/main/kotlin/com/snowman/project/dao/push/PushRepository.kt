package com.snowman.project.dao.push

import com.snowman.project.model.push.entity.PushMessage
import com.snowman.project.model.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PushRepository : JpaRepository<PushMessage, Long> {
    fun findAllByUserOrderByCreatedAtDesc(user: User, pageable: Pageable): Page<PushMessage>
}