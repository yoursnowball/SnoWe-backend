package com.snowman.project.config.exceptions

import java.time.LocalDateTime

class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int
) {
    val timeStamp: LocalDateTime = LocalDateTime.now()
}