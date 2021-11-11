package com.snowman.project.model

import javax.persistence.*

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "goal_id")
    val goal: Goal,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    ) : BaseTimeEntity()
