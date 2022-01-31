package com.snowman.project.auth.controller.req

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty

@ApiModel("로그인 요청")
data class SignInRequest(
        @ApiModelProperty("아이디")
        @field:NotEmpty
        val userName: String,
        @ApiModelProperty("비밀번호")
        @field:NotEmpty
        val password: String
) {
}