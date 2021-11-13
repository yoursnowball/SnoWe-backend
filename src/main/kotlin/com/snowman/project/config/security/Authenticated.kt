package com.snowman.project.config.security


@Target(AnnotationTarget.VALUE_PARAMETER,AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated()
