package io.github.xxxspring.base.util.jwt

import java.io.Serializable

/**
 * Created by xponly on 17/11/19.
 */
class JWTInfo : Serializable, IJWTInfo {
    override var uid: String
        set(value) {field = value}
        get() = field

    override var username: String
        set(value) {field = value}
        get() = field

    override var tenantId: String
        set(value) {field = value}
        get() = field

    override var nonce: Long
        set(value) {field = value}
        get() = field

    override var uniqueName: String? = null
    get() = this.username

    constructor(uid: String, username: String, tenantId: String, nonce: Long) {
        this.uid= uid
        this.username = username
        this.tenantId = tenantId
        this.nonce = nonce
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val jwtInfo = o as JWTInfo?

        if (username != o.username) {
            return false
        }
        return uid == o.uid
    }

    override fun hashCode(): Int {
        var result = username?.hashCode() ?: 0
        result = 31 * result + (uid?.hashCode() ?: 0)
        return result
    }
}
