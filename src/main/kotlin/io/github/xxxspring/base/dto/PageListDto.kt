package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@ApiModel("PageListDto")
open class PageListDto<T>(

        @ApiModelProperty("数据列表", required = true)
        @field:NotEmpty
        open val data: List<T>?,

        @ApiModelProperty("相关数据", required = true)
        @field:NotNull
        open val extra: PageExtraDto?
)
