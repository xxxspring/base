package com.lf.ec.common.base.entity

open class CursorExtra(
        open val hasMore: Boolean?,
        open val min: Any?,
        open val max: Any?,
        override val totalCount: Long?
) : Extra(totalCount)