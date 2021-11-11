package com.snowman.project.model

import javax.persistence.*


@Entity
@Table(name = "awards")
data class Awards(
    @Id
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