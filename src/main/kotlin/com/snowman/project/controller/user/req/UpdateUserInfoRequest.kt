package com.snowman.project.controller.user.req

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ApiModel("사용자 정보 수정")
data class UpdateUserInfoRequest(
        @ApiModelProperty("닉네임")
        @field:NotBlank
        val nickName: String,
        @ApiModelProperty("푸시 알람 시간 (HH:mm)")
        @field:NotNull
        val alarmTime: LocalTime
) {
}