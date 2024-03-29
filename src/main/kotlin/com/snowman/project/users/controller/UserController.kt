package com.snowman.project.users.controller

import com.snowman.project.security.AuthInfo
import com.snowman.project.security.Authenticated
import com.snowman.project.users.controller.req.RegisterTokenRequest
import com.snowman.project.users.controller.res.GetAlarmHistoryResponse
import com.snowman.project.users.controller.res.GetUserInfoResponse
import com.snowman.project.users.controller.res.RegisterFcmTokenResponse
import com.snowman.project.notifications.domain.dto.PushHistoryDto
import com.snowman.project.notifications.domain.enums.PushType
import com.snowman.project.goals.service.GoalService
import com.snowman.project.notifications.service.PushService
import com.snowman.project.users.service.UserService
import com.snowman.project.common.utils.page.PageResponse
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
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

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("내 정보 가져오기")
    @GetMapping
    fun getMyInfo(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo
    ): GetUserInfoResponse {
        val userId = authInfo.id
        return GetUserInfoResponse(
            userService.getUserInfo(userId),
            goalService.getMyTodayActiveGoals(userId)
        )
    }

    @ResponseStatus(HttpStatus.OK)
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


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Fcm token 삭제")
    @DeleteMapping("/token")
    fun deleteFcmToken(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
    ) {
        val userId = authInfo.id
        userService.deleteToken(userId)
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("알람 이력 가져오기")
    @GetMapping("/alarms")
    fun getAlarmHistory(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam page: Int
    ): PageResponse<PushHistoryDto> {
        val userId = authInfo.id
        val result = userService.getAlarmHistory(userId, page)
        return PageResponse(
            result.content,
            result.number,
            result.isLast
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
}