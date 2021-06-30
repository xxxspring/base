package com.lf.ec.common.mysql.service.impl

import com.lf.ec.common.base.entity.*
import com.lf.ec.common.base.service.CrudService
import com.lf.ec.common.mysql.util.MysqlUtil
import org.slf4j.LoggerFactory
import tk.mybatis.mapper.common.Mapper
import tk.mybatis.mapper.entity.Example
import tk.mybatis.mapper.mapperhelper.EntityHelper
import java.lang.reflect.ParameterizedType

abstract class CrudMapperServiceImpl<K, T> : CrudService<K, T> {

    private val logger by lazy { LoggerFactory.getLogger(CrudMapperServiceImpl::class.java) }

    protected abstract val mapper: Mapper<T>

    override fun create(entity: T): Int {
        return mapper.insertSelective(entity)
    }

    override fun retrieve(id: K): T? {
        return mapper.selectByPrimaryKey(id)
    }

    @Suppress("UNCHECKED_CAST")
    override fun batchRetrieve(ids: List<K>): List<T>? {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        val example = Example(clazz)
        val pkIds = EntityHelper.getPKColumns(clazz)

        if (ids.isEmpty()) {
            throw IllegalArgumentException("ids is empty!")
        }

        if (pkIds.size > 1) {
            throw IllegalStateException("pkid can't more than one!")
        }

        example.createCriteria().andIn(pkIds.elementAt(0).column, ids)
        return mapper.selectByExample(example)
    }

    override fun update(entity: T): Int {
        return mapper.updateByPrimaryKeySelective(entity)
    }

    override fun delete(id: K): Int {
        return mapper.deleteByPrimaryKey(id)
    }

    @Suppress("UNCHECKED_CAST")
    override fun batchDelete(ids: List<K>) {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        val example = Example(clazz)
        val pkIds = EntityHelper.getPKColumns(clazz)

        if (pkIds.size > 1) {
            throw IllegalStateException("pkid can't more than one")
        }

        example.createCriteria().andIn(pkIds.elementAt(0).column, ids)
        mapper.deleteByExample(example)
    }

    @Suppress("UNCHECKED_CAST")
    override fun page(query: PageQuery): PageList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return MysqlUtil.page(clazz, mapper, query)
    }

    @Suppress("UNCHECKED_CAST")
    override fun cursor(cursorQuery: CursorQuery): CursorList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return MysqlUtil.cursor(clazz, mapper, cursorQuery)
    }
}
