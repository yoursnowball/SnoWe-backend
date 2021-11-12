package com.snowman.project.controller.auth.req

import javax.validation.constraints.NotEmpty

data class SignInRequest(
    @field:NotEmpty
    val userName: String,
    @field:NotEmpty
    val password: String
) {
}