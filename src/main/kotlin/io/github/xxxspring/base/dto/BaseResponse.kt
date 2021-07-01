package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("Result")
open class BaseResponse(
        @ApiModelProperty("错误码，0表示成功", required = true)
        open val code: Int? = null,

        @ApiModelProperty("错误信息")
        open val msg: String? = null

) : Serializable {
    constructor() : this(
            code = 0,
            msg = ""
    )
}
