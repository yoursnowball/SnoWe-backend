package com.snowman.project.controller.awards.res

import com.snowman.project.model.awards.dto.AwardInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("명예의 전당 리스트")
class GetAwardsResponse(
        @ApiModelProperty("명예의 전당 리스트")
        val awards: List<AwardInfoDto>
) {
}