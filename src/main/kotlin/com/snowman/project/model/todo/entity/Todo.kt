package com.snowman.project.model.todo.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.entity.Goal
import java.time.LocalDateTime
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

    @Column(name = "succeed", nullable = false)
    var succeed: Boolean = false,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null

) : BaseTimeEntity() {

    fun update(name: String, succeed: Boolean): Boolean {
        this.name = name
        if (!this.succeed && succeed) {
            this.succeed = succeed
            this.finishedAt = LocalDateTime.now()
            return true
        }
        return false
    }
}
