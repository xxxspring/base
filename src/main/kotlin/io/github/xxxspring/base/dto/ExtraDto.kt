package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("ExtraDto")
data class ExtraDto<T>(
        @ApiModelProperty("总数", required = true)
        val totalCount: Long
)
