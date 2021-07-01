package io.github.xxxspring.base.entity

import io.github.xxxspring.base.dto.ExtFieldDto
import io.github.xxxspring.base.exception.ExtFieldTypeErrorException
import java.io.Serializable

open class ExtField(
        // 字段名，如：volume
        var key: String? = null,

        // 字段标题，如：体积
        var title: String? = null,

        // 数据类型，如：long
        var type: String? = null,

        // 默认值，如：0，需要根据数据类型进行转型
        var defaultValue: String? = null
) : Serializable {
    companion object {
        fun parseValue(value: String, type: String?): Any {
            return when (type) {
                "long" -> value.toLong()
                "double" -> value.toDouble()
                "string" -> value
                else -> value
            }
        }


        fun toDto(entity: ExtField): ExtFieldDto {
            return ExtFieldDto(
                    key = entity.key,
                    title = entity.title,
                    type = entity.type,
                    defaultValue = if (entity.defaultValue == null) null else parseValue(entity.defaultValue!!, entity.type!!)
            )
        }

        fun fromDto(dto: ExtFieldDto): ExtField {
            if (dto.type != "long" && dto.type != "double" && dto.type != "string") {
                throw ExtFieldTypeErrorException(dto.type!!)
            }


            return ExtField(
                    key = dto.key,
                    title = dto.title,
                    type = dto.type,
                    defaultValue = dto.defaultValue.toString()
            )
        }


    }
}
