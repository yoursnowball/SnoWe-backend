package com.snowman.project.config.exceptions

import com.google.firebase.messaging.FirebaseMessagingException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorResponse> {
        val errorCode = ex.errorCode
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())

        return ResponseEntity(errorResponse, httpStatus)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleRequestValid(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CONSTRAINT_VIOLATION
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())
        return ResponseEntity(errorResponse, httpStatus)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.METHOD_ARGUMENT_NOTVALID
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())
        return ResponseEntity(errorResponse, httpStatus)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequestValid(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.METHOD_ARGUMENT_TYPE_MISTMATCH
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())
        return ResponseEntity(errorResponse, httpStatus)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleBadRequestBody(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.HTTP_MESSAGE_NOT_READABLE
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())
        return ResponseEntity(errorResponse, httpStatus)
    }

    @ExceptionHandler(FirebaseMessagingException::class)
    fun handlePushAlarmException(ex: FirebaseMessagingException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.PUSH_MESSAGE_ERROR
        val httpStatus = errorCode.status
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message, httpStatus.value())
        return ResponseEntity(errorResponse, httpStatus)
    }
}