package com.snowman.project.service.common.event

import org.springframework.context.ApplicationEventPublisher

object Events {

    private val publisherLocal: ThreadLocal<ApplicationEventPublisher> = ThreadLocal()

    fun raise(event: DomainEvent) {
        if (publisherLocal.get() != null) {
            publisherLocal.get().publishEvent(event);
        }else
            println(event.message + "I'm not started")
    }

    fun setPublisher(publisher: ApplicationEventPublisher) {
        publisherLocal.set(publisher)
    }

    fun reset() {
        publisherLocal.remove()
    }
}