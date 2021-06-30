package com.lf.ec.common.base.entity

data class CursorPage<T>(
        val hasMore: Boolean,
        val maxCursor: Any?,
        val minCursor: Any?,
        val data: List<T>
)