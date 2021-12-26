package com.snowman.project.model.awards.entity

import com.snowman.project.model.common.entity.BaseEntity
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.awards.event.AwardSaveEvent
import com.snowman.project.model.common.entity.DomainEvent
import org.springframework.data.domain.AfterDomainEventPublication
import javax.persistence.Column
import javax.persistence.Entity
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
) : BaseEntity() {
    fun publishSaveEvent(): Award {
        events.add(AwardSaveEvent(this))
        return this
    }
}