package com.snowman.project.model.todo.entity

import com.snowman.project.model.common.entity.BaseEntity
import com.snowman.project.model.common.entity.DomainEvent
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.todo.event.TodoCheckedUpdateEvent
import com.snowman.project.service.todo.event.TodoUnCheckedUpdateEvent
import org.springframework.data.domain.AfterDomainEventPublication
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "todos")
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
    val todoDate: LocalDate
) : BaseEntity() {

    fun update(name: String, succeed: Boolean): Todo {
        this.name = name
        /**
         * UnCheck -> Check
         */
        if (!this.succeed && succeed) {
            this.succeed = succeed
            this.finishedAt = LocalDateTime.now()
            events.add(TodoCheckedUpdateEvent(this))
        }
        /**
         * Check -> Uncheck
         */
        else if (this.succeed && !succeed) {
            this.succeed = succeed
            this.finishedAt = null
            events.add(TodoUnCheckedUpdateEvent(this))
        }
        return this
    }

    fun canUpdateOrDelete(): Boolean {
        return todoDate.isAfter(LocalDate.now()) || todoDate.isEqual(LocalDate.now())
    }
}
