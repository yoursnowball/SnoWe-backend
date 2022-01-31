package com.snowman.project.common.exception

open class ApiException(
    val errorCode: ErrorCode
) : RuntimeException() {
}