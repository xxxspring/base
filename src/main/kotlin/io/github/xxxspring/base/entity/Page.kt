package io.github.xxxspring.base.entity

data class Page<T>(
        val content: List<T>,
        val total: Long,
        val pageNo: Int,
        val pageSize: Int
)
