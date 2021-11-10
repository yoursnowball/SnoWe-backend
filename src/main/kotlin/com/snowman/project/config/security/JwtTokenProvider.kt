package com.snowman.project.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
open class JwtTokenProvider(
    @Value("\${app.jwt.secret}") private val jwtSecret: String,
) {


}