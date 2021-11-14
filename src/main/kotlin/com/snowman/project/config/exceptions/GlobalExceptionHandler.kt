package com.snowman.project.config.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorResponse> {
        val errorCode = ex.errorCode
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())

        return ResponseEntity(errorResponse, httpStatus)
    }
}