package com.lf.ec.common.base.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.*

@ApiModel("ImageDto", description = "图片信息")
data class ImageDto(

        @ApiModelProperty("是否动画", required = true)
        @field:NotNull
        var isAnimated: Boolean? = null,

        @ApiModelProperty("图片类型", required = true)
        @field:PositiveOrZero
        var type: Int? = null,

        @ApiModelProperty("文件大小（字节）", required = true)
        @field:Positive
        var size: Int? = null,

        @ApiModelProperty("图片id，比如 http://oss.www.com/jsoidjfoiffef, uri 就是 jsoidjfoiffef", required = true)
        @field:NotBlank
        var uri: String? = null,

        @ApiModelProperty("图片宽度（像素）", required = true)
        @field:Positive
        var width: Int? = null,

        @ApiModelProperty("图片高度（像素）", required = true)
        @field:Positive
        var height: Int? = null,

        @ApiModelProperty("图片链接地址", required = true)
        @field:NotEmpty
        var urls: List<String>? = null,

        @ApiModelProperty("图片的颜色均值，如#ccc", required = true)
        @field:NotBlank
        var avgColor: String? = null
)
