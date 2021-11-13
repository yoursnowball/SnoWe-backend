package com.snowman.project.model.goal

import com.snowman.project.model.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "goals")
data class Goal(
    @Id
    @GeneratedValue
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
