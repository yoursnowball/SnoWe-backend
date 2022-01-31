package com.snowman.project.awards.controller.res

import com.snowman.project.awards.dao.projections.AwardInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("명예의 전당 리스트")
class GetAwardsResponse(
        @ApiModelProperty("명예의 전당 리스트")
        val awards: List<AwardInfoDto>
) {
}