package com.snowman.project.awards.controller

import com.snowman.project.security.AuthInfo
import com.snowman.project.security.Authenticated
import com.snowman.project.awards.controller.res.GetAwardsResponse
import com.snowman.project.awards.dao.projections.AwardInfoDto
import com.snowman.project.awards.service.AwardService
import com.snowman.project.common.utils.page.PageResponse
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/awards")
class AwardController(
    val awardService: AwardService
) {
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("내 명예의전당 리스트 가져오기")
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

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "명예의 전당 랭킹 가져오기")
    @GetMapping("/rank")
    fun getAwardsByRanking(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam page: Int?
    ): PageResponse<AwardInfoDto> {
        val awards = awardService.getAwardsByRanking(page ?: 1)
        return PageResponse(
            awards.content,
            awards.number,
            awards.isLast
        )
    }
}