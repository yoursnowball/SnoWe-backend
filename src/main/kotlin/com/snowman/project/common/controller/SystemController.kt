package com.snowman.project.common.controller

import com.snowman.project.notifications.domain.enums.PushType
import com.snowman.project.notifications.service.PushMessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemController(
    private val pushMessageService: PushMessageService
) {

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    fun healthChecker(): String {
        return "pong"
    }

    @GetMapping("/push")
    @ResponseStatus(HttpStatus.OK)
    fun sendPushMessage(
        @RequestParam type: PushType
    ) {
        pushMessageService.send(type)
    }
}
