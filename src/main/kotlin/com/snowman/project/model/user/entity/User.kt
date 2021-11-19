package com.snowman.project.model.user.entity

import com.snowman.project.model.common.BaseTimeEntity
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_name", nullable = false, unique = false)
    val userName: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "nickname", nullable = false, length = 20, unique = false)
    var nickName: String,

    @Column(name = "alarm_time")
    var alarmTime: LocalTime? = null,

    @Column(name = "fcm_token")
    var fcmToken: String? = null
) : BaseTimeEntity() {

    fun update(nickName: String, alarmTime: LocalTime) {
        this.nickName = nickName
        this.alarmTime = alarmTime
    }
}
