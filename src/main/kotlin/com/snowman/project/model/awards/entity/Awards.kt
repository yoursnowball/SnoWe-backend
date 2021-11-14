package com.snowman.project.model.awards.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import javax.persistence.*


@Entity
@Table(name = "awards")
data class Awards(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    val goal: Goal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User

) : BaseTimeEntity() {
}