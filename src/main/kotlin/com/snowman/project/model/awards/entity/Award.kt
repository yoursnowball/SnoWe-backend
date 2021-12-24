package com.snowman.project.model.awards.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import javax.persistence.*


@Entity
@Table(name = "awards")
class Award(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    val goal: Goal,

    @Column(name = "total_todo_count")
    val totalTodoCount: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User

) : BaseTimeEntity() {
}