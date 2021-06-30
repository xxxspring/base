package com.lf.ec.common.mysql.util

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.lf.ec.common.base.entity.*
import com.lf.ec.common.base.util.CursorTypeUtil
import tk.mybatis.mapper.common.Mapper
import tk.mybatis.mapper.entity.Example
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.util.*

/**
 * Created by xponly on 17/11/3.
 */
object MysqlUtil {
    val classFieldMap = mutableMapOf<Class<*>, MutableMap<String, Field>>()


    /**
     * 这里 多个 and 条件需要测试
     * 超过2级的嵌套也会有问题
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> buildCriteria(clazz: Class<T>, example: Example, filters: Map<String, Any>?): Example.Criteria {
        val criteria = example.createCriteria()

        // 第一次才反射, 后面都缓存了
        var fieldMap = classFieldMap[clazz]
        if (fieldMap == null) {
            fieldMap = mutableMapOf()
            clazz.declaredFields.forEach {
                fieldMap[it.name] = it
            }
            classFieldMap[clazz] = fieldMap
        }


        filters?.forEach { filter ->
            when (filter.key) {
                FilterType.AND.name -> {
                    val subFilters = filter.value as List<Map<String, Any>>
                    subFilters.forEach { subFilter ->
                        val subCriteria = buildCriteria(clazz, example, subFilter)
                        // TODO 这里无法嵌套子查询, 比较恶心, 这样只能最多嵌套1级, AND 下面无法支持 OR子查询了
                        criteria.criteria.addAll(subCriteria.allCriteria)
                    }
                }
                FilterType.OR.name -> {
                    val subFilters = filter.value as List<Map<String, Any>>
                    // TODO 无法嵌套子查询，所以 example 只能支持最多下一级的 or
                    subFilters.forEach { subFilter -> example.or(buildCriteria(clazz, example, subFilter)) }
                }
                FilterType.NOR.name -> {
                }
                FilterType.IS_NULL.name -> {
                    criteria.andIsNull(filter.value as String)
                }
                else -> {   // property
                    val property = filter.key
                    val vMap = filter.value as Map<String, Any>

                    vMap.forEach {
                        var value: Any = it.value
                        val field = fieldMap[property]
                        if (field != null) {
                            if (field.type == Date::class.java) {
                                if (value is String) {
                                    value = Date.parse(value)
                                } else if (value is Number) {
                                    value = Date(value.toLong())
                                }
                            }
                        }

                        when (it.key) {
                            FilterType.EQ.name -> criteria.andEqualTo(property, value)
                            FilterType.GT.name -> criteria.andGreaterThan(property, value)
                            FilterType.GTE.name -> criteria.andGreaterThanOrEqualTo(property, value)
                            FilterType.LT.name -> criteria.andLessThan(property, value)
                            FilterType.LTE.name -> criteria.andLessThanOrEqualTo(property, value)
                            FilterType.LIKE.name -> criteria.andLike(property, "%" + value.toString() + "%")
                            FilterType.NE.name -> criteria.andNotEqualTo(property, value)
                            FilterType.NOT_LIKE.name -> criteria.andNotLike(property, "%" + value.toString() + "%")
                            FilterType.NOT_IN.name -> criteria.andNotIn(property, value as List<*>)
                            FilterType.IN.name -> criteria.andIn(property, value as List<*>)
                        }
                    }
                }
            }
        }

        return criteria
    }


    fun <T> page(clazz: Class<T>, mapper: Mapper<T>, query: PageQuery): PageList<T> {
        val example = Example(clazz)
        buildCriteria(clazz, example, query.filters)

        val sortStrArr = mutableListOf<String>()
        if (query.sort != null) {
            for (sort in query.sort!!.iterator()) {
                when (sort.type) {
                    SortType.DSC -> sortStrArr.add("`${sort.property}` DESC")
                    SortType.ASC -> sortStrArr.add("`${sort.property}` ASC")
                    SortType.DEFAULT -> sortStrArr.add("`${sort.property}`")
                }
            }

            if (sortStrArr.isNotEmpty()) {
                val orderByClause = sortStrArr.joinToString(separator = ",")
                example.orderByClause = orderByClause
            }
        }
        PageHelper.startPage<T>(query.pageNo!!.toInt(), query.pageSize!!.toInt())

        val list = mapper.selectByExample(example)
        val page = PageInfo<T>(list)

        var count = page.total
        val pageExtra = PageExtra(query.pageNo!!, query.pageSize!!, count)
        val pageList = PageList(page.list, pageExtra)
        return pageList
    }

    fun <T> count(clazz: Class<T>, mapper: Mapper<T>, filters: Map<String, Any>): Int {
        val example = Example(clazz)
        buildCriteria(clazz, example, filters)
        return mapper.selectCountByExample(example)
    }

    fun <T> query(clazz: Class<T>, mapper: Mapper<T>, query: PageQuery): List<T> {
        val example = Example(clazz)
        buildCriteria(clazz, example, query.filters)


        val sortStrArr = mutableListOf<String>()
        if (query.sort != null) {
            for (sort in query.sort!!.iterator()) {
                when (sort.type) {
                    SortType.DSC -> sortStrArr.add("`${sort.property}` DESC")
                    SortType.ASC -> sortStrArr.add("`${sort.property}` ASC")
                    SortType.DEFAULT -> sortStrArr.add("`${sort.property}`")
                }
            }

            if (sortStrArr.isNotEmpty()) {
                val orderByClause = sortStrArr.joinToString(separator = ",")
                example.orderByClause = orderByClause
            }
        }

        return mapper.selectByExample(example)
    }

    fun <T> cursor(clazz: Class<T>, mapper: Mapper<T>, cursor: CursorQuery): CursorList<T> {
        val example = Example(clazz)
        val criteria = buildCriteria(clazz, example, cursor.filters)

        if (cursor.cursor !== null) {
            val cursorFiled = clazz.getDeclaredField(cursor.cursorSort!!.property.toString())
            if (cursorFiled === null) {
                throw IllegalArgumentException("don't exist ${cursor.cursor}")
            }

            val orginType = cursorFiled.type
            val paramType = cursor.cursor!!::class.java
            //时间戳转换
            if (orginType === Date::class.java && paramType === java.lang.Long::class.java) {
                cursor.cursor = Date(cursor.cursor as Long)
            }


            when (cursor.direction) {
                1 -> {
                    criteria.andGreaterThan(cursor.cursorSort!!.property, cursor.cursor)
                }
                else -> {
                    criteria.andLessThan(cursor.cursorSort!!.property, cursor.cursor)
                }
            }
        }
        val cursorSort = cursor.cursorSort
        when (cursorSort!!.type) {
            SortType.DSC -> example.orderByClause = "`${cursorSort.property}` DESC"
            SortType.ASC -> example.orderByClause = "`${cursorSort.property}` ASC"
            SortType.DEFAULT -> example.orderByClause = "`${cursorSort.property}`"
        }

        var count = mapper.selectByExample(example).size.toLong()

        PageHelper.startPage<T>(1, cursor.size!! + 1)
        var list = mapper.selectByExample(example)

        var hasMore = false
        if (list.size > 1 && list.size > cursor.size!!) {
            hasMore = true
            list.removeAt(cursor.size!!)
        }

        val cursorField = clazz.getDeclaredField(cursor.cursorSort!!.property)
        cursorField.isAccessible = true

        val minValue = if (list.isEmpty()) null else cursorField.get(list.first())
        val minCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(minValue!!)

        val maxValue = if (list.isEmpty()) null else cursorField.get(list.last())
        val maxCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(maxValue!!)

        val cursorExtra = CursorExtra(hasMore, minCursor, maxCursor, count)
        val cusorList = CursorList(list, cursorExtra)
        return cusorList
    }


}