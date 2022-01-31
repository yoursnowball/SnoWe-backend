package com.snowman.project.todos.domain.entity

import com.snowman.project.common.domain.entity.AbstractAggregateEntity
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.todos.domain.event.TodoCheckedEvent
import com.snowman.project.todos.domain.event.TodoUncheckedEvent
import com.snowman.project.users.model.entity.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "todos")
@EntityListeners(AuditingEntityListener::class)
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "goal_id")
    val goal: Goal,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    @Column(name = "succeed", nullable = false)
    var succeed: Boolean = false,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null,

    @Column(name = "todo_date")
    val todoDate: LocalDate,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null


) : AbstractAggregateEntity() {

    fun update(name: String, succeed: Boolean) {
        this.name = name
        /**
         * UnCheck -> Check
         */
        if (!this.succeed && succeed) {
            this.succeed = succeed
            this.finishedAt = LocalDateTime.now()
            registerEvent(TodoCheckedEvent(goal.id!!))
        }
        /**
         * Check -> Uncheck
         */
        else if (this.succeed && !succeed) {
            this.succeed = succeed
            this.finishedAt = null
            registerEvent(TodoUncheckedEvent(goal.id!!))
        }
    }

    fun canUpdateOrDelete(): Boolean {
        return todoDate.isAfter(LocalDate.now()) || todoDate.isEqual(LocalDate.now())
    }
}
