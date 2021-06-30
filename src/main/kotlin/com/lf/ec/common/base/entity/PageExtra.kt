package com.lf.ec.common.base.entity

open class PageExtra(
        open val pageNo: Int?,
        open val pageSize: Int?,
        override val totalCount: Long?
) : Extra(totalCount)