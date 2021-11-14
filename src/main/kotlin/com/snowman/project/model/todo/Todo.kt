package com.snowman.project.model.todo

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.entity.Goal
import javax.persistence.*

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "goal_id")
    val goal: Goal,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    ) : BaseTimeEntity()
