package io.github.xxxspring.base.entity

data class CursorQuery(
        var filters: Map<String, Any>? = null,
        var cursor: Any? = null,
        var cursorSort: SortSpec? = null,
        var size: Int? = 20,
        var direction: Int? = 1
)
