package com.snowman.project.notifications.domain.entity

import com.snowman.project.notifications.domain.enums.PushStatus
import com.snowman.project.users.model.entity.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "push_messages")
@EntityListeners(AuditingEntityListener::class)
class PushMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: User,

    @Column
    val title: String,

    @Column
    val body: String,

    @Enumerated(EnumType.STRING)
    var status: PushStatus = PushStatus.UNREAD,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null

) {
    fun readAlarm() {
        status = PushStatus.READ
    }
}