package com.snowman.project.service.common.event

import org.springframework.context.ApplicationEventPublisher

class Events {
    companion object {

        private val publisherLocal: ThreadLocal<ApplicationEventPublisher> = ThreadLocal()

        fun raise(event: DomainEvent) {
            publisherLocal.get().publishEvent(event)
        }

        fun setPublisher(publisher: ApplicationEventPublisher) {
            publisherLocal.set(publisher)
        }

        fun reset() {
            publisherLocal.remove()
        }
    }
}