package com.snowman.project.controller.awards

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.awards.res.GetAwardsResponse
import com.snowman.project.dao.awards.projections.AwardInfoDto
import com.snowman.project.service.awards.AwardService
import com.snowman.project.utils.page.PageResponse
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