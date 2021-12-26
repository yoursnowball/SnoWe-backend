package com.snowman.project.model.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity() {
    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @Transient
    protected val events: MutableList<DomainEvent> = mutableListOf()

    @DomainEvents
    fun events(): Collection<DomainEvent> {
        return events
    }

    @AfterDomainEventPublication
    fun clearEvents() {
        events.clear()
    }

}