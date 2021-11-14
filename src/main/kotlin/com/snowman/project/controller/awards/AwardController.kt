package com.snowman.project.controller.awards

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.awards.res.GetAwardsResponse
import com.snowman.project.service.awards.AwardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AwardController(
    val awardService: AwardService
) {

    @GetMapping
    fun getMyAwards(
        @Authenticated authInfo: AuthInfo
    ): GetAwardsResponse {
        val userId = authInfo.id
        return GetAwardsResponse(
            awardService.getMyAwards(userId)
        )
    }
}