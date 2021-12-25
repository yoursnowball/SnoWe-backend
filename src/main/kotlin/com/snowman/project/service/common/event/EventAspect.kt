package com.snowman.project.service.common.event

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component


@Aspect
@Component
class EventAspect : ApplicationEventPublisherAware {

    lateinit var eventPublisher: ApplicationEventPublisher
    private val appliedLocal = ThreadLocal<Boolean>()

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun handleEvent(joinPoint: ProceedingJoinPoint): Any {
        val appliedValue = appliedLocal.get()
        var nested  = false

        if (appliedValue != null && appliedValue) {
            nested = true
        } else {
            nested = false
            appliedLocal.set(true)
        }

        if (!nested) Events.setPublisher(eventPublisher)

        try {
            return joinPoint.proceed()
        } finally {
            if (!nested) {
                Events.reset()
                appliedLocal.remove()
            }
        }
    }
}