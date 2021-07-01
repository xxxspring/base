package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@ApiModel("PageExtraDto")
data class PageExtraDto(

        @ApiModelProperty("总数", required = true)
        @field:PositiveOrZero
        val totalCount: Long?,

        @ApiModelProperty("当前页码", required = true)
        @field:Positive
        val pageNo: Int?,

        @ApiModelProperty("翻页大小", required = true)
        @field:Positive
        val pageSize: Int?
)
