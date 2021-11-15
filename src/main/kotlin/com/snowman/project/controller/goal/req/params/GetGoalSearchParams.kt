package com.snowman.project.controller.goal.req.params

import java.time.LocalDate

data class GetGoalSearchParams(
    val startDate: LocalDate?,
    val endDate: LocalDate?
) {
}