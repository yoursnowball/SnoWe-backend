package com.snowman.project.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp() {

    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    fun signIn() {

    }
}