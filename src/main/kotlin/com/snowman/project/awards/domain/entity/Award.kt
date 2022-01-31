package com.snowman.project.awards.domain.entity

import com.snowman.project.awards.domain.event.AwardCreateEvent
import com.snowman.project.common.domain.entity.AbstractAggregateEntity
import com.snowman.project.goals.domain.entity.Goal
import com.snowman.project.users.model.entity.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "awards")
@EntityListeners(AuditingEntityListener::class)
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
    val user: User,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null

) : AbstractAggregateEntity() {
    fun publishCreateEvent(): Award {
        registerEvent(AwardCreateEvent(goal.id!!))
        return this
    }
}