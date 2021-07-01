package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("PageQueryDto")
data class PageQueryDto(
        @ApiModelProperty("过滤条件")
        val filters: Map<String, Any>? = null,

        @ApiModelProperty("排序")
        val sort: List<SortSpecDto>? = null,

        @ApiModelProperty("页码")
        val pageNo: Int? = 1,

        @ApiModelProperty("页数")
        val pageSize: Int? = 20
)
