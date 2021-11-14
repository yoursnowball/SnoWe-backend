package com.snowman.project.model.awards.dto

import com.snowman.project.model.awards.entity.Awards
import com.snowman.project.model.goal.enums.CharacterType
import java.time.LocalDateTime

data class AwardInfoDto(
    val id: Long,
    val name: String,
    val awardAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val succeedTodoCount: Int,
    val type: CharacterType,
    val level: Int
) {
    constructor(award: Awards) : this(
        award.id!!,
        award.goal.name,
        award.createAt!!,
        award.goal.createAt!!,
        award.goal.succeedTodoCount,
        award.goal.characterType,
        award.goal.level
    )
}