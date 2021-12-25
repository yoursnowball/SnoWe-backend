package com.snowman.project.model.awards.entity

import com.snowman.project.service.awards.event.AwardSaveEvent
import com.snowman.project.model.common.entity.BaseTimeEntity
import com.snowman.project.service.common.event.Events
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.model.user.entity.User
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
    val user: User

) : BaseTimeEntity() {
    fun publishSaveEvent(): Award {
        Events.raise(AwardSaveEvent(this))
        return this
    }
}