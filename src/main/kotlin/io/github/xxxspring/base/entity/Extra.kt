package io.github.xxxspring.base.entity

import java.io.Serializable

open class Extra(
        open val totalCount: Long? = null
): Serializable {
    constructor() : this(
            totalCount = 0
    )
}
