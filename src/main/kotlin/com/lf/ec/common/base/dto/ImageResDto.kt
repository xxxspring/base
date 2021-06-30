package com.lf.ec.common.base.dto

import java.util.*

data class ImageResDto(
        var id: Long? = null,

        var type: Int? = null,

        var size: Int? = null,

        var width: Int? = null,

        var height: Int? = null,

        var isAnimated: Boolean? = null,

        var uri: String? = null,

        var urls: List<String>? = null,

        var avgColor: String? = null,

        var ctime: Date? = null,

        var utime: Date? = null
)

