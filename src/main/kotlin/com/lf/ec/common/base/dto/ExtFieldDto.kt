package com.lf.ec.common.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel("ExtFieldDto")
data class ExtFieldDto(

        @ApiModelProperty("字段名，如：volume", required = true)
        @field:NotBlank
        var key: String? = null,

        @ApiModelProperty("字段标题，如：体积", required = true)
        @field:NotBlank
        var title: String? = null,

        @ApiModelProperty("数据类型 string/long/double，如：long", required = true)
        var type: String? = null,

        @ApiModelProperty("默认值，如：0")
        var defaultValue: Any? = null
)
