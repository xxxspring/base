package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("UpdateDto")
open class UpdateDto<T>(

    @ApiModelProperty("更新前 信息")
    open val pre: T? = null,

    @ApiModelProperty("更新后 信息")
    open val current: T? = null
) : Serializable


