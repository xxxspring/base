package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CursorListDto")
open class CursorListDto<T>(

        @ApiModelProperty("数据列表", required = true)
        open val data: List<T>,

        @ApiModelProperty("相关数据", required = true)
        open val extra: CursorExtraDto
)
