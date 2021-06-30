package com.lf.ec.common.base.entity

open class PageList<T>(
        override val data: List<T>?,
        override val extra: PageExtra?
) : DataList<T, PageExtra>(data, extra)