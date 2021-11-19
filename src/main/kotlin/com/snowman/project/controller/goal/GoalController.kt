package com.snowman.project.controller.goal

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.awards.res.GetAwardResponse
import com.snowman.project.controller.goal.req.body.SaveGoalRequest
import com.snowman.project.controller.goal.res.GetGoalResponse
import com.snowman.project.controller.goal.res.GetGoalsResponse
import com.snowman.project.service.awards.AwardService
import com.snowman.project.service.goal.GoalService
import io.swagger.annotations.ApiOperation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/goals")
class GoalController(
    val goalService: GoalService,
    val awardService: AwardService
) {

    @ApiOperation("기간내에 제일 많이 수행한 목표 리스트 리턴")
    @GetMapping("/calendar")
    @ResponseStatus(HttpStatus.OK)
    fun getGoals(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        start: LocalDate,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        end: LocalDate

    ): GetGoalsResponse {
        val userId = authInfo.id
        return GetGoalsResponse(
            goalService.getBestDailyGoalsByDates(
                userId,
                start,
                end
            )
        )
    }

    @ApiOperation("목표 생성하기")
    @PostMapping
    fun saveGoal(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @Valid @RequestBody req: SaveGoalRequest
    ): GetGoalResponse {
        val userId = authInfo.id

        return GetGoalResponse(
            goalService.saveGoal(userId, req.name, req.objective, req.type),
        )
    }

    @ApiOperation("목표 명예의 전당으로 보내기")
    @PostMapping("/{goalId}/awards")
    fun moveToAwards(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam goalId: Long
    ): GetAwardResponse {
        val userId = authInfo.id
        return GetAwardResponse(awardService.saveAwards(userId, goalId))
    }

    @ApiOperation("목표 가져오기")
    @GetMapping("/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    fun getGoal(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?
    ): GetGoalResponse {
        val userId = authInfo.id
        val targetDate = date ?: LocalDate.now()
        return GetGoalResponse(goalService.getMyGoal(userId, goalId, targetDate))
    }

    @ApiOperation("목표 삭제")
    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGoal(
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long
    ) {
        val userId = authInfo.id
        goalService.deleteGoal(userId, goalId)
    }
}