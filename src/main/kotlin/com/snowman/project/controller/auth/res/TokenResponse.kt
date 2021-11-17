package com.snowman.project.controller.auth.res

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("accessToken")
data class TokenResponse(
        @ApiModelProperty("토큰")
        val token: String
) {
}