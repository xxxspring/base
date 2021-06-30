package com.lf.ec.common.base.multitenant

import org.springframework.stereotype.Component

@Component("tenantProvider")
class TenantProvider {
    val tenantId: String?
    get() {
        return TenantContextHolder.get()
    }
}