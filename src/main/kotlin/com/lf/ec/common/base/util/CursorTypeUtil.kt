package com.lf.ec.common.base.util

import java.lang.IllegalArgumentException
import java.util.*

object CursorTypeUtil {

    fun transformType(value: Any): Any{

        when(value.javaClass){


            Date::class.java -> {
                return (value as Date).time
            }

            Int::class.java -> {
                return value as Long
            }

            Long::class.java -> {
                return value as Long
            }

            java.lang.Long::class.java -> {
                return value as Long
            }

            java.lang.Integer::class.java -> {
                return value.toString().toLong()
            }

            String::class.java -> {
                return value
            }

            else -> {
                throw IllegalArgumentException("can't transform type $value ${value.javaClass}")
            }
        }


    }

}