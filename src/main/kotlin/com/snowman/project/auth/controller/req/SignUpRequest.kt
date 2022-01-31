package com.snowman.project.auth.controller.req

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty

@ApiModel("회원가입 요청")
data class SignUpRequest(
        @ApiModelProperty("아이디")
        @field:NotEmpty
        val userName: String,
        @ApiModelProperty("비밀번호")
        @field:NotEmpty
        val password: String,
        @ApiModelProperty("닉네임")
        @field:NotEmpty
        val nickName: String
) {
}