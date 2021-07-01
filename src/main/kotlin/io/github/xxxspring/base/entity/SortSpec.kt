package io.github.xxxspring.base.entity

data class SortSpec(
        val property: String,
        val type: SortType = SortType.DEFAULT,
        val ignoreCase: Boolean = true
)
