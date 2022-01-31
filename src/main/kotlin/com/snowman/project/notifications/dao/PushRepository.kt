package com.snowman.project.notifications.dao

import com.snowman.project.notifications.domain.entity.PushMessage
import com.snowman.project.users.model.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PushRepository : JpaRepository<PushMessage, Long> {
    fun findAllByUserOrderByCreatedAtDesc(user: User, pageable: Pageable): Page<PushMessage>
}