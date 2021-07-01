package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModelProperty

data class CountDto(
        @ApiModelProperty("统计数", required = true)
        var count: Int? = null
)
