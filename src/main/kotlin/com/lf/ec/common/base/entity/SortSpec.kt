package com.lf.ec.common.base.entity

data class SortSpec(
        val property: String,
        val type: SortType = SortType.DEFAULT,
        val ignoreCase: Boolean = true
)
