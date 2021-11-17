package com.snowman.project.controller.awards

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.awards.res.GetAwardsResponse
import com.snowman.project.service.awards.AwardService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/awards")
class AwardController(
        val awardService: AwardService
) {

    @ApiOperation("명예의전당 리스트 가져오기")
    @GetMapping
    fun getMyAwards(
            @ApiIgnore
            @Authenticated authInfo: AuthInfo
    ): GetAwardsResponse {
        val userId = authInfo.id
        return GetAwardsResponse(
                awardService.getMyAwards(userId)
        )
    }
}