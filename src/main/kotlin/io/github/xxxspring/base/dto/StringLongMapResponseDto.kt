package io.github.xxxspring.base.dto

import io.github.xxxspring.base.dto.DataResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("StringLongMapResponseDto")
class StringLongMapResponseDto(
        @ApiModelProperty("返回数据", required = true)
        override val data: Map<String, Long>?
): DataResponse<Map<String, Long>>(data) {

}
