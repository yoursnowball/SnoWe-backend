package com.snowman.project.controller.user

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.user.req.UpdateUserInfoRequest
import com.snowman.project.controller.user.res.GetUserInfoResponse
import com.snowman.project.controller.user.res.UpdateUserInfoResponse
import com.snowman.project.model.user.dto.SimpleUserInfoDto
import com.snowman.project.service.goal.GoalService
import com.snowman.project.service.user.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService,
    val goalService: GoalService
) {

    /**
     * 자기 정보 가져오기
     */
    @GetMapping
    fun getMyInfo(
        @Authenticated authInfo: AuthInfo
    ): GetUserInfoResponse {
        val userId = authInfo.id
        return GetUserInfoResponse(
            userService.getUserInfo(userId),
            goalService.getMyGoals(userId)
        )
    }

    /**
     * 자기 정보 수정
     */
    @PutMapping
    fun updateMyInfo(
        @Authenticated authInfo: AuthInfo,
        @Valid @RequestBody reqUser: UpdateUserInfoRequest
    ): UpdateUserInfoResponse {
        return UpdateUserInfoResponse(
            userService.updateUserInfo(
                authInfo.id,
                SimpleUserInfoDto(
                    reqUser.nickName,
                    reqUser.alarmTime
                )
            )
        )
    }
}