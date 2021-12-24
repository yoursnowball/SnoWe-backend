package com.snowman.project.model.push.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.push.enums.PushStatus
import com.snowman.project.model.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "push_messages")
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
    var status: PushStatus = PushStatus.UNREAD

) : BaseTimeEntity() {
    fun readAlarm() {
        status = PushStatus.READ
    }
}