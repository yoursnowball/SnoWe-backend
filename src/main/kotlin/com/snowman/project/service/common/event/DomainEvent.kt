package com.snowman.project.service.common.event

import org.springframework.context.ApplicationEvent

open class DomainEvent(source: Any, val message: String) : ApplicationEvent(source) {
}