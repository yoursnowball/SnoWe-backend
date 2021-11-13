package com.snowman.project.controller.goal

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/goals")
class GoalController {

    /**
     * 부캐리스트 가져오기
     */
    @GetMapping
    fun getGoals(@Authenticated authInfo: AuthInfo) {
    }

    /**
     * Goal(부캐) 생성
     */
    @PostMapping
    fun saveGoal() {

    }

    /**
     * Goal(부캐) 가져오기
     */
    @GetMapping("/{id}")
    fun getGoal(@PathVariable id: Long) {

    }

    /**
     * 부캐삭제
     */
    @DeleteMapping("/{id}")
    fun deleteGoal(@PathVariable id: Long) {

    }


}