package com.snowman.project.config.exceptions

enum class ErrorCode(
    val code: String,
    val message: String,
) {
    DUPLICATE_USERNAME("AUTH-001", "중복된 아이디입니다."),
    DUPLICATE_NICKNAME("AUTH-002", "중복된 아이디입니다."),
    WRONG_LOGIN_INFO("AUTH-003", "가입하지 않은 아이디이거나, 잘못된 비밀번호 입니다."),
    INVALID_TOKEN("AUTH-004", "유효하지 않은 토큰입니다"),
    MALFORMED_TOKEN("AUTH-005", "손상된 토큰입니다."),
    EXPIRE_TOKEN("AUTH-006", "만료된 토큰입니다."),
    NOT_BEARER_FORMAT("AUTH-007", "Bearer가 포함되어있지 않습니다."),
}