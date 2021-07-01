package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CursorExtraDto")
data class CursorExtraDto(
        @ApiModelProperty("总数", required = true)
        val totalCount: Long,

        @ApiModelProperty("是否还有", required = true)
        val hasMore: Boolean,

        @ApiModelProperty("最小值", required = true, dataType = "java.lang.String")
        val min: Any?,

        @ApiModelProperty("最大值", required = true, dataType = "java.lang.String")
        val max: Any?
)
