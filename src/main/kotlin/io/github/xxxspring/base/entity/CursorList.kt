package io.github.xxxspring.base.entity

open class CursorList<T>(
        override val data: List<T>?,
        override val extra: CursorExtra?
) : DataList<T, CursorExtra>(data, extra)
