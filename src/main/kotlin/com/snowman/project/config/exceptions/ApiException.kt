package com.snowman.project.config.exceptions

open class ApiException(
    val errorCode: ErrorCode
) : RuntimeException() {
}