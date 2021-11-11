package com.snowman.project.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "goals")
data class Goal(
    @Id
    val id: Long? = null,

    @Column(name = "name", length = 20, nullable = false)
    val name: String,

    @Column(name = "succeed_todo_count")
    var succeedTodoCount: Int = 0,

    @Column(name = "level")
    var level: Int = 0,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,
) : BaseTimeEntity()
