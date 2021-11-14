package com.snowman.project.config.exceptions

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus
) {
    DUPLICATE_USERNAME("AUTH-001", "중복된 아이디입니다.", HttpStatus.BAD_REQUEST),
    WRONG_LOGIN_INFO("AUTH-002", "가입하지 않은 아이디이거나, 잘못된 비밀번호 입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("AUTH-003", "유효하지 않은 토큰입니다", HttpStatus.FORBIDDEN),
    MALFORMED_TOKEN("AUTH-004", "손상된 토큰입니다.", HttpStatus.FORBIDDEN),
    EXPIRE_TOKEN("AUTH-005", "만료된 토큰입니다.", HttpStatus.FORBIDDEN),
    NOT_BEARER_FORMAT("AUTH-006", "Header에 Bearer가 포함되어있지 않습니다.", HttpStatus.FORBIDDEN),

    USER_NOT_EXIST("USER-001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    GOAL_NOT_EXIST("GOAL-001", "목표를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    NOT_YOUR_CONTENT("SYSTEM-001", "내 컨텐츠가 아닙니다.", HttpStatus.BAD_REQUEST),
    DELETED_CONTENT("SYSTEM-002", "삭제된 컨텐츠가 아닙니다.", HttpStatus.BAD_REQUEST)
}