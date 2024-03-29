package com.snowman.project.goals.controller

import com.snowman.project.security.AuthInfo
import com.snowman.project.security.Authenticated
import com.snowman.project.awards.controller.res.GetAwardResponse
import com.snowman.project.goals.controller.req.SaveGoalRequest
import com.snowman.project.goals.controller.res.GetDailyGoalsResponse
import com.snowman.project.goals.controller.res.GetGoalInfoResponse
import com.snowman.project.goals.controller.res.GetGoalsInfoResponse
import com.snowman.project.awards.service.AwardService
import com.snowman.project.goals.service.GoalService
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

    @ApiOperation("해당 날짜의 나의 목표와 투두리스트 리턴")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getGoals(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ): GetDailyGoalsResponse {
        val userId = authInfo.id
        return GetDailyGoalsResponse(
            goalService.getMyDailyGoalsHistory(userId, date)
        )
    }

    @ApiOperation("기간내에 제일 많이 수행한 목표 리스트 리턴")
    @GetMapping("/calendar")
    @ResponseStatus(HttpStatus.OK)
    fun getBestDailyGoals(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        start: LocalDate,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        end: LocalDate

    ): GetGoalsInfoResponse {
        val userId = authInfo.id
        return GetGoalsInfoResponse(
            goalService.getBestDailyGoalByDates(
                userId,
                start,
                end
            )
        )
    }

    @ApiOperation("목표 생성하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveGoal(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @Valid @RequestBody req: SaveGoalRequest
    ): GetGoalInfoResponse {
        val userId = authInfo.id

        return GetGoalInfoResponse(
            goalService.saveGoal(userId, req.name, req.objective, req.type),
        )
    }

    @ApiOperation("목표 명예의 전당으로 보내기")
    @PostMapping("/{goalId}/awards")
    @ResponseStatus(HttpStatus.OK)
    fun moveToAwards(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long
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
    ): GetGoalInfoResponse {
        val userId = authInfo.id
        val targetDate = date ?: LocalDate.now()
        return GetGoalInfoResponse(goalService.getMyGoal(userId, goalId, targetDate))
    }

    @ApiOperation("목표 삭제")
    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGoal(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long
    ) {
        val userId = authInfo.id
        goalService.deleteGoal(userId, goalId)
    }
}