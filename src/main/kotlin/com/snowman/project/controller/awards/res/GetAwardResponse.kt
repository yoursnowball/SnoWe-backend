package com.snowman.project.controller.awards.res

import com.snowman.project.model.awards.dto.AwardInfoDto
import com.snowman.project.model.goal.enums.CharacterType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("명예의 전당 정보")
class GetAwardResponse(
    @ApiModelProperty("명예의 전당 식별자")
    val id: Long,
    @ApiModelProperty("명예의 전당 이름")
    val name: String,
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
    constructor(dto: AwardInfoDto) : this(
        dto.id,
        dto.name,
        dto.awardAt,
        dto.createdAt,
        dto.succeedTodoCount,
        dto.totalTodoCount,
        dto.type,
        dto.level
    )
}