package com.lf.ec.common.base.model

import java.io.Serializable
import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * Created by xponly on 18/2/20.
 */
open class IdentityDomain<K>: Serializable {
    private val id: K? = null

    private val ctime: OffsetDateTime? = null

    private val utime: OffsetDateTime? = null

    private val dtime: OffsetDateTime? = null

    companion object {
        val serialVersionUID = 2806569656265256243L

        val DEFAULT_DATE_TIME = OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(8))
    }
}