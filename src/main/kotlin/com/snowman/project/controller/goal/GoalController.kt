package com.snowman.project.controller.goal

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.goal.req.SaveGoalRequest
import com.snowman.project.controller.goal.res.GetGoalResponse
import com.snowman.project.controller.goal.res.GetGoalsResponse
import com.snowman.project.service.goal.GoalService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/goals")
class GoalController(
    val goalService: GoalService
) {

    /**
     * 부캐리스트 가져오기
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getGoals(@Authenticated authInfo: AuthInfo): GetGoalsResponse {
        val userId = authInfo.id

        return GetGoalsResponse(goalService.getMyGoals(userId))
    }

    /**
     * Goal(부캐) 생성
     */
    @PostMapping
    fun saveGoal(
        @Authenticated authInfo: AuthInfo,
        @Valid @RequestBody req: SaveGoalRequest
    ): GetGoalResponse {
        val userId = authInfo.id

        return GetGoalResponse(
            goalService.saveGoal(userId, req.name, req.type)
        )
    }

    /**
     * Goal(부캐) 가져오기
     */
    @GetMapping("/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    fun getGoal(
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long
    ): GetGoalResponse {
        val userId = authInfo.id

        return GetGoalResponse(goalService.getMyGoal(userId, goalId))
    }

    /**
     * 부캐삭제
     */
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