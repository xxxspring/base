package io.github.xxxspring.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("DataResponse", description = "返回数据")
open class DataResponse<T>(

        @ApiModelProperty("错误码，0代表成功", required = true)
        override val code: Int,

        @ApiModelProperty("错误消息")
        override val msg: String?,

        @ApiModelProperty("返回数据", required = true)
        open val data: T?

) : BaseResponse(code, msg) {

    constructor(data: T?) : this(
            code = 0,
            msg = "",
            data = data
    )
}
