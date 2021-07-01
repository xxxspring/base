package io.github.xxxspring.base.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

/**
 * js 的 number 只支持 53bit 整数，超过范围时序列化为字符串
 */
class LongSerializer : JsonSerializer<Any>() {

    private val maxSafeInteger = 9007199254740991
    private val minSafeInteger = -9007199254740991

    override fun serialize(value: Any?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        val param = (value as Long).toLong()
        if (param in minSafeInteger..maxSafeInteger) {
            gen!!.writeNumber(value.toLong())
        } else {
            gen!!.writeString(value.toString())
        }
    }

    companion object {
        val instance = LongSerializer()
    }
}
