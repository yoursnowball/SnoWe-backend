package com.snowman.project.controller.user.res

import com.snowman.project.model.user.dto.SimpleUserInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalTime

@ApiModel("내 정보 수정")
data class UpdateUserInfoResponse(
        @ApiModelProperty("닉네임")
        val nickName: String,
        @ApiModelProperty("푸시 알람 시간 (HH:mm)")
        val alarmTime: LocalTime
) {
    constructor(dto: SimpleUserInfoDto) : this(dto.nickName, dto.alarmTime)
}