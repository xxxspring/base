package com.lf.ec.common.base.util

/**
 * Description: TODO
 * Author: jim
 * Date: 2018/10/5
 * Time: 下午3:53
 * Copyright (C) 2018 QiaoQiao Inc. All rights reserved.
 */
class StringHelper {
    companion object {
        fun getObjectValue(obj: Any?): String {
            return obj?.toString() ?: ""
        }
    }
}