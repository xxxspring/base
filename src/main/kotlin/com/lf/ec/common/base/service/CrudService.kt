package com.lf.ec.common.base.service

import com.lf.ec.common.base.entity.*

interface CrudService<K, T> {

    fun retrieve(id: K): T?

    fun batchRetrieve(ids: List<K>): List<T>?

    fun create(entity: T): Int

    fun update(entity: T): Int

    fun delete(id: K): Int

    fun batchDelete(ids: List<K>)

    fun page(query: PageQuery): PageList<T>

    fun cursor(cursorQuery: CursorQuery): CursorList<T>
}
