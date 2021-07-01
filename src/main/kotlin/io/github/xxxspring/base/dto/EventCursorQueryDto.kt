package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("EventCursorQueryDto")
class EventCursorQueryDto(
        @ApiModelProperty("id")
        var id: String? = null,

        @ApiModelProperty("游标属性(1:游标从前向后移动 0:游标从后向前移动)", required = true)
        var cursor: Long = 0,

        @ApiModelProperty("分页大小", required = true)
        var size: Int = 20
)
