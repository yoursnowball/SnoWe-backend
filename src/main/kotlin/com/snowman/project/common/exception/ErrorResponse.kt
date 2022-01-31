package com.snowman.project.common.exception

import java.time.LocalDateTime

class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int
) {
    val timeStamp: LocalDateTime = LocalDateTime.now()
}