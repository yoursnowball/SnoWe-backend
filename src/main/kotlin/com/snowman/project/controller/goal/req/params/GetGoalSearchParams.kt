package com.snowman.project.controller.goal.req.params

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

@ApiModel("목표 검색 파라미터")
data class GetGoalSearchParams(
        @ApiModelProperty("시작 날짜 (yyyy-MM-dd)")
        val startDate: LocalDate?,
        @ApiModelProperty("종료 날짜 (yyyy-MM-dd)")
        val endDate: LocalDate?
) {
}