package com.lf.ec.common.base.util

import org.springframework.lang.Nullable

class LongUtil {

    companion object {
        fun isZero(num: Long?): Boolean {
            return num == null || num == 0L
        }
    }

}