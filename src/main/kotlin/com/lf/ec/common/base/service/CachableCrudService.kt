package com.lf.ec.common.base.service

interface CachableCrudService<CK, K, V> : CrudService<K, V> {

    fun cacheKeyByEntity(entity: V): CK

    fun cacheKeyById(id: K): CK
}