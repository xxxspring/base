package io.github.xxxspring.base.entity

open class PageExtra(
        open val pageNo: Int?,
        open val pageSize: Int?,
        override val totalCount: Long?
) : Extra(totalCount)
