package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CursorPageDto")
data class CursorPageDto<T> (
        @ApiModelProperty("游标方向是否仍有数据")
        val hasMore: Boolean,

        @ApiModelProperty("当前最大游标值", dataType = "java.lang.String")
        val maxCursor: Any?,

        @ApiModelProperty("当前最小游标值", dataType = "java.lang.String")
        val minCursor: Any?,

        @ApiModelProperty("数据列表")
        val data: List<T>
)
