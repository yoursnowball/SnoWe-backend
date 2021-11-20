package com.snowman.project.dao.push

import com.snowman.project.model.push.entity.PushMessage
import com.snowman.project.model.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface PushRepository : JpaRepository<PushMessage, Long> {
    fun findAllByUser(user: User): List<PushMessage>
}