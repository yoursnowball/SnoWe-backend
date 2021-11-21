package com.snowman.project.controller.user.res

import com.snowman.project.model.goal.dto.DetailGoalInfoDto
import com.snowman.project.model.user.dto.DetailUserInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("내 정보")
data class GetUserInfoResponse(
    @ApiModelProperty("닉네임")
    val nickName: String,
    @ApiModelProperty("가입 일 (yyyy-MM-dd HH:mm:ss)")
    val createdAt: LocalDateTime,
    @ApiModelProperty("현재 진행중인 목표 개수")
    val goalCount: Int,
    @ApiModelProperty("목표들")
    val goals: List<DetailGoalInfoDto?>
) {
    constructor(dto: DetailUserInfoDto, goals: List<DetailGoalInfoDto?>) : this(
        dto.nickName,
        dto.createdAt,
        goals.size,
        goals.toMutableList().let {
            while (it.size < 4)
                it.add(null)
            it
        }
    )
}