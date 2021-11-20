package com.snowman.project.controller.user.req

import javax.validation.constraints.NotEmpty

class RegisterTokenRequest(
    @field:NotEmpty
    val token: String
) {
}