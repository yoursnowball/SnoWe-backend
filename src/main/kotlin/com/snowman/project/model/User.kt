package com.snowman.project.model

import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: Long? = null,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "nickname", nullable = false, length = 20)
    val nickName: String,

    @Column(name = "alarm_time")
    var alarmTime: LocalTime? = null,

    @Column(name = "fcm_token")
    var fcmToken: String? = null
) : BaseTimeEntity() {

}
