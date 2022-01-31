package com.snowman.project.awards.dao.projections

import com.querydsl.core.annotations.QueryProjection
import com.snowman.project.awards.domain.entity.Award
import com.snowman.project.goals.domain.enums.CharacterType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("명예의 전당 정보")
data class AwardInfoDto @QueryProjection constructor(
    @ApiModelProperty("명예의 전당 식별자")
    val id: Long,
    @ApiModelProperty("명예의 전당에 등록된 목표 이름")
    val name: String,
    @ApiModelProperty("명예의 전당에 등록한 유저 이름")
    val userName: String,
    @ApiModelProperty("명예의 전당에등록된 목표 상세")
    val objective: String,
    @ApiModelProperty("명예의 전당에 등록된 날짜 (yyyy-MM-dd HH:mm:ss)")
    val awardAt: LocalDateTime,
    @ApiModelProperty("목표가 생성된 날짜 (yyyy-MM-dd HH:mm:ss)")
    val createdAt: LocalDateTime,
    @ApiModelProperty("성공한 투두 개수")
    val succeedTodoCount: Int,
    @ApiModelProperty("등록된 투두 개수")
    val totalTodoCount: Int,
    @ApiModelProperty("캐릭터 종류")
    val type: CharacterType,
    @ApiModelProperty("레벨")
    val level: Int
) {
    constructor(award: Award) : this(
        award.id!!,
        award.goal.name,
        award.user.userName,
        award.goal.objective,
        award.createdAt!!,
        award.goal.createdAt!!,
        award.goal.succeedTodoCount,
        award.totalTodoCount,
        award.goal.characterType,
        award.goal.level
    )
}