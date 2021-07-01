package io.github.xxxspring.base.mysql.service.impl

import tk.mybatis.mapper.common.Mapper

open class DefaultCrudMapperServiceImpl<K, T>(m: Mapper<T>) : CrudMapperServiceImpl<K, T>() {

    private val defaultMapper = m

    override val mapper: Mapper<T> get() = defaultMapper
}
