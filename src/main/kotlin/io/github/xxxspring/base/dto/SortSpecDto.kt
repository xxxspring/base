package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("SortSpecDto")
data class SortSpecDto (
        @ApiModelProperty("排序属性", required = true)
        val property: String,

        @ApiModelProperty("排序类型,由小到大：ASC,由大到小：DSC,默认:DEFAULT", required = true)
        val type: String,

        @ApiModelProperty("是否忽略大小写,默认忽略", required = true)
        val ignoreCase: Boolean = true
)
