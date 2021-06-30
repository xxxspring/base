package com.lf.ec.common.base.entity


data class PageQuery(
        val filters: Map<String, Any>? = null,
        val sort: List<SortSpec>? = null,
        val pageNo: Int? = 1,
        val pageSize: Int? = 20
)