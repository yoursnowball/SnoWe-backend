package com.snowman.project.model.awards

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.Goal
import com.snowman.project.model.user.entity.User
import javax.persistence.*


@Entity
@Table(name = "awards")
data class Awards(
        @Id
    @GeneratedValue
    val id: Long? = null,

        @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    val goal: Goal,

        @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User


) : BaseTimeEntity() {
}