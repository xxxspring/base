package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("PageDto")
data class PageDto<T>(

        @ApiModelProperty("数据列表", required = true)
        val content: List<T>,

        @ApiModelProperty("总数", required = true)
        val total: Long,

        @ApiModelProperty("当前页码", required = true)
        val pageNo: Int,

        @ApiModelProperty("翻页大小", required = true)
        val pageSize: Int
)
