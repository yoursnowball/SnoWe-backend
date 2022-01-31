package com.snowman.project.users.model.entity

import com.snowman.project.common.domain.entity.AbstractAggregateEntity
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "users", indexes = [Index(columnList = "fcm_token")])
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_name", nullable = false, unique = false)
    val userName: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "nickname", nullable = false, length = 20, unique = false)
    var nickName: String,

    @Column(name = "fcm_token")
    var fcmToken: String? = null,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null

) : AbstractAggregateEntity() {
    fun registerFcmToken(token: String): String {
        fcmToken = token
        return fcmToken!!
    }

    fun deleteFcmToken() {
        fcmToken = null
    }
}
