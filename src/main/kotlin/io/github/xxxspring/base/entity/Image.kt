package io.github.xxxspring.base.entity

import io.github.xxxspring.base.dto.ImageDto
import io.github.xxxspring.base.dto.ImageResDto


/**
 * 图片信息
 */
data class Image(
        var id: Long? = null,

        // 是否动画
        var isAnimated: Boolean? = null,

        // 图片格式 TODO enum
        var type: Int? = null,

        // 文件大小（字节）
        var size: Int? = null,

        // 图片id，比如 http://oss.www.com/jsoidjfoiffef, uri 就是 jsoidjfoiffef
        var uri: String? = null,

        // 图片宽度（像素）
        var width: Int? = null,

        // 图片高度（像素）
        var height: Int? = null,

        // 图片链接地址
        var urls: List<String>? = null,

        // 图片的颜色均值，如#ccc
        var avgColor: String? = null
) {
    companion object {

        fun toDto(entity: Image): ImageDto {
            return ImageDto(
                    isAnimated = entity.isAnimated,
                    type = entity.type,
                    size = entity.size,
                    uri = entity.uri,
                    width = entity.width,
                    height = entity.height,
                    urls = entity.urls,
                    avgColor = entity.avgColor
            )
        }

        fun fromResDto(dto: ImageResDto): Image {
            return Image(
                    id = dto.id,
                    isAnimated = dto.isAnimated,
                    type = dto.type,
                    size = dto.size,
                    uri = dto.uri,
                    width = dto.width,
                    height = dto.height,
                    urls = dto.urls,
                    avgColor = dto.avgColor
            )
        }

    }
}
