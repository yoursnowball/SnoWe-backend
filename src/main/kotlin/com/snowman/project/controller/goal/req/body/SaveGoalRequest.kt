package com.snowman.project.controller.goal.req.body

import com.snowman.project.model.goal.enums.CharacterType

data class SaveGoalRequest(
    val name: String,
    val type: CharacterType
) {
}