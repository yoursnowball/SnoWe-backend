package com.snowman.project.model.common.entity

import org.springframework.context.ApplicationEvent

open class DomainEvent(source: Any, val message: String) : ApplicationEvent(source) {
}