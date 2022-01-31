package com.snowman.project.users.controller.req

import javax.validation.constraints.NotEmpty

class RegisterTokenRequest(
    @field:NotEmpty
    val token: String
) {
}