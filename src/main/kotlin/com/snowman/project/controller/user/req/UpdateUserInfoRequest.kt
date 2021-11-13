package com.snowman.project.controller.user.req

import java.time.LocalTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UpdateUserInfoRequest(
        @field:NotBlank
        val nickName: String,
        @field:NotNull
        val alarmTime: LocalTime
) {
}