package io.github.xxxspring.base.context

import io.github.xxxspring.base.constant.CommonConstants
import io.github.xxxspring.base.util.StringHelper
import java.util.*

/**
 * Description: TODO
 * Author: jim
 * Date: 2018/10/7
 * Time: 下午8:57
 * Copyright (C) 2018 QiaoQiao Inc. All rights reserved.
 */
object BaseContextHandler {
    var threadLocal = ThreadLocal<Map<String, Any>>()

    var userID: String?
        get() {
            val value = get(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_ID)
            return returnObjectValue(value)
        }
        set(userID) = set(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_ID, userID
                ?: "")

    var username: String?
        get() {
            val value = get(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USERNAME)
            return returnObjectValue(value)
        }
        set(username) = set(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USERNAME, username
                ?: "")


    var name: String
        get() {
            val value = get(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_NAME)
            return StringHelper.getObjectValue(value)
        }
        set(name) = set(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_NAME, name)

    var token: String
        get() {
            val value = get(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_TOKEN)
            return StringHelper.getObjectValue(value)
        }
        set(token) = set(io.github.xxxspring.base.constant.CommonConstants.CONTEXT_KEY_USER_TOKEN, token)

    operator fun set(key: String, value: Any) {
        var map: MutableMap<String, Any>? = threadLocal.get().toMutableMap()
        if (map == null) {
            map = HashMap()
            threadLocal.set(map)
        }
        map.put(key, value)
    }

    operator fun get(key: String): Any? {
        var map: Map<String, Any>? = threadLocal.get()
        if (map == null) {
            map = HashMap()
            threadLocal.set(map)
        }
        return map[key]
    }

    private fun returnObjectValue(value: Any?): String? {
        return value?.toString()
    }

    fun remove() {
        threadLocal.remove()
    }
}
