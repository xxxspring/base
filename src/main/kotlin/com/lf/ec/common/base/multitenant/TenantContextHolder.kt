package com.lf.ec.common.base.multitenant

object TenantContextHolder {
    private val tenantId = ThreadLocal<String?>()

    fun set(value: String?) {
        tenantId.set(value)
    }

    fun get(): String? {
        return tenantId.get()
    }

    fun clear() {
        tenantId.remove()
    }
}