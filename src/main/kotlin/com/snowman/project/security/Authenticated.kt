package com.snowman.project.security


@Target(AnnotationTarget.VALUE_PARAMETER,AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated()
