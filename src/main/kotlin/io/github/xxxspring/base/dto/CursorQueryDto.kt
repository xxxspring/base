package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CursorQueryDto")
data class CursorQueryDto (
        @ApiModelProperty("过滤条件", required = true)
        var filters: Map<String, Any>? = null,

        @ApiModelProperty("游标属性(1:游标从前向后移动 0:游标从后向前移动)", required = true, dataType = "java.lang.String")
        var cursor: Any? = null,

        @ApiModelProperty("排序", required = true)
        var cursorSort: SortSpecDto? = null,

        @ApiModelProperty("分页大小", required = true)
        var size: Int? = 20,

        @ApiModelProperty("游标方向", required = true)
        var direction: Int? = 1
)
