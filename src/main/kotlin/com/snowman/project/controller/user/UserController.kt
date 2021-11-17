package com.snowman.project.controller.user

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.user.req.UpdateUserInfoRequest
import com.snowman.project.controller.user.res.GetUserInfoResponse
import com.snowman.project.controller.user.res.UpdateUserInfoResponse
import com.snowman.project.model.user.dto.SimpleUserInfoDto
import com.snowman.project.service.goal.GoalService
import com.snowman.project.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
        val userService: UserService,
        val goalService: GoalService
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

    @ApiOperation("내 정보 수정")
    @PutMapping
    fun updateMyInfo(
            @ApiIgnore
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