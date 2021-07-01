package io.github.xxxspring.base.dto

import io.github.xxxspring.base.dto.DataResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("LongListResponseDto")
class LongListResponseDto(
        @ApiModelProperty("返回数据", required = true)
        override val data: List<Long>?
): DataResponse<List<Long>>(data) {

}
