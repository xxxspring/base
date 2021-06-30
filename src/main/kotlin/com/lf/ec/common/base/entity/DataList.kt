package com.lf.ec.common.base.entity

open class DataList<T, E: Extra>(
        open val data: List<T>?,
        open val extra: E?
)