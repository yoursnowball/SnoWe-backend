package com.snowman.project.controller.auth

import com.snowman.project.controller.auth.req.SignInRequest
import com.snowman.project.controller.auth.req.SignUpRequest
import com.snowman.project.controller.auth.res.TokenResponse
import com.snowman.project.service.auth.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody req: SignUpRequest): TokenResponse {
        return TokenResponse(authService.signUp(req.userName, req.password, req.nickName))
    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    fun signIn(
        @Valid @RequestBody req: SignInRequest
    ): TokenResponse {
        return TokenResponse(authService.signIn(req.userName, req.password))
    }
}