package com.snowman.project.config.exceptions

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus
) {
    DUPLICATE_USERNAME("AUTH-001", "중복된 아이디입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_NICKNAME("AUTH-002", "중복된 닉네임입니다.", HttpStatus.BAD_REQUEST),
    WRONG_LOGIN_INFO("AUTH-003", "가입하지 않은 아이디이거나, 잘못된 비밀번호 입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("AUTH-004", "유효하지 않은 토큰입니다", HttpStatus.FORBIDDEN),
    MALFORMED_TOKEN("AUTH-005", "손상된 토큰입니다.", HttpStatus.FORBIDDEN),
    EXPIRE_TOKEN("AUTH-006", "만료된 토큰입니다.", HttpStatus.FORBIDDEN),
    NOT_BEARER_FORMAT("AUTH-007", "Header에 Bearer가 포함되어있지 않습니다.", HttpStatus.FORBIDDEN),

    USER_NOT_EXIST("USER-001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    GOAL_NOT_EXIST("GOAL-001", "목표를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_GOAL("GOAL-002", "명예의전당에 등록된 목표는 삭제 할 수 없습니다.", HttpStatus.BAD_REQUEST),

    TODO_NOT_EXIST("TODO-001", "투두를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_SUCCEED_TODO("TODO-002", "성공상태인 투두는 삭제 할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_ADD_TODO("TODO-003", "삭제되었거나, 명예의전당에 올라간 목표에는 투두를 작성할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_EDIT_PAST_TODO("TODO-004", "과거의 투두는 수정하거나 삭제 할 수 없습니다.", HttpStatus.BAD_REQUEST),

    NOT_YOUR_CONTENT("SYSTEM-001", "내 컨텐츠가 아닙니다.", HttpStatus.BAD_REQUEST),
    DELETED_CONTENT("SYSTEM-002", "삭제된 컨텐츠가 입니다.", HttpStatus.BAD_REQUEST),
    DATE_RANGE_EXCEPTION("SYSTEM-003", "유효하지 않은 날짜 범위입니다.", HttpStatus.BAD_REQUEST),

    CANNOT_MOVE_TO_AWARDS("AWARD-001", "명예의 전당으로 이동하기 충분한 레벨이 아닙니다.", HttpStatus.BAD_REQUEST)


}