package io.github.xxxspring.base.util

import java.util.*

/**
 * Description: UUID
 * Author: jim
 * Date: 2018/8/3
 * Time: 下午3:51
 * Copyright (C) 2018 QiaoQiao Inc. All rights reserved.
 */
class UUIDUtil {
    companion object {
        fun getUUID(): String {
            val uuid = UUID.randomUUID().toString()
            return uuid.replace("-", "")
        }
    }
}
