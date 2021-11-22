package com.snowman.project.controller.user

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.user.req.RegisterTokenRequest
import com.snowman.project.controller.user.res.GetAlarmHistoriesResponse
import com.snowman.project.controller.user.res.GetAlarmHistoryResponse
import com.snowman.project.controller.user.res.GetUserInfoResponse
import com.snowman.project.controller.user.res.RegisterFcmTokenResponse
import com.snowman.project.service.goal.GoalService
import com.snowman.project.service.push.PushService
import com.snowman.project.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService,
    val goalService: GoalService,
    val pushService: PushService
) {

    @ApiOperation("내 정보 가져오기")
    @GetMapping
    fun getMyInfo(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo
    ): GetUserInfoResponse {
        val userId = authInfo.id
        return GetUserInfoResponse(
            userService.getUserInfo(userId),
            goalService.getMyGoals(userId)
        )
    }

    @ApiOperation("Fcm token 저장 및 수정")
    @PostMapping("/token")
    fun registerFcmToken(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @Valid @RequestBody req: RegisterTokenRequest
    ): RegisterFcmTokenResponse {
        val userId = authInfo.id
        return RegisterFcmTokenResponse(userService.registerFcmToken(userId, req.token))
    }

    @ApiOperation("알람 이력 가져오기")
    @GetMapping("/alarms")
    fun getAlarmHistory(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo
    ): GetAlarmHistoriesResponse {
        val userId = authInfo.id
        return GetAlarmHistoriesResponse(
            userService.getAlarmHistory(userId)
        )
    }

    @ApiOperation("알람 읽음 처리")
    @PutMapping("/alarms/{alarmId}")
    fun readAlarm(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable alarmId: Long
    ): GetAlarmHistoryResponse {
        return GetAlarmHistoryResponse(
            pushService.readAlarm(alarmId)
        )
    }

    @ApiOperation("Push Test Controller")
    @GetMapping("/push-test")
    fun pushTest(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo
    ) {
        userService.testPush(authInfo.id)
    }
}