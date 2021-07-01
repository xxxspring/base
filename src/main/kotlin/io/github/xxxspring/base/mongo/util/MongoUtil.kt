package io.github.xxxspring.base.mongo.util

import io.github.xxxspring.base.entity.*
import io.github.xxxspring.base.util.CursorTypeUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.util.*

object MongoUtil {
    val classFieldMap = mutableMapOf<Class<*>, MutableMap<String, Field>>()


    @Suppress("UNCHECKED_CAST")
    fun <T> buildCriteria(clazz: Class<T>, query: Query, filters: Map<String, Any>?) {

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
//                    val criteria = Criteria()
//                    val subFilters = filter.value as List<Map<String, Any>>
//                    subFilters.forEach { subFilter ->
//                        subFilter.forEach{
//                            criteria.and().
//
//                        }
//                        val subCriteria = buildCriteria(clazz, example, subFilter)
//                        // TODO 这里无法嵌套子查询, 比较恶心, 这样只能最多嵌套1级, AND 下面无法支持 OR子查询了
//                        criteria.and(subFilter.)
//                    }
                }
                FilterType.OR.name -> {
//                    val subFilters = filter.value as List<Map<String, Any>>
//                    // TODO 无法嵌套子查询，所以 example 只能支持最多下一级的 or
//                    subFilters.forEach { subFilter -> example.or(buildCriteria(clazz, example, subFilter)) }
                }
                FilterType.NOR.name -> {
                }
                else -> {   // property
                    val property = filter.key
                    val vMap = filter.value as Map<String, Any>
                    val criteria = Criteria(property)
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
                            FilterType.EQ.name -> criteria.`is`(value)
                            FilterType.GT.name -> criteria.gt(value)
                            FilterType.GTE.name -> criteria.gte(value)
                            FilterType.LT.name -> criteria.lt(value)
                            FilterType.LTE.name -> criteria.lte(value)
                            FilterType.NE.name -> criteria.ne(value)
                            FilterType.NOT_IN.name -> criteria.nin(value as List<*>)
                            FilterType.IN.name -> criteria.`in`(value as List<*>)
                        }
                    }
                    query.addCriteria(criteria)
                }
            }
        }

    }

    fun <T> page(clazz: Class<T>, mongoTemplate: MongoTemplate, pageQuery: PageQuery): PageList<T> {
        val query = Query()
        buildCriteria(clazz, query, pageQuery.filters)

        val pageSort = pageQuery.sort
        val sort: Sort
        pageSort?.forEach {
            var sort = when (it!!.type) {
                SortType.DSC -> Sort.by(Sort.Direction.DESC, it.property)
                SortType.ASC -> Sort.by(Sort.Direction.ASC, it.property)
                SortType.DEFAULT -> Sort.by(Sort.DEFAULT_DIRECTION, it.property)
            }
            query.with(sort)
        }

        // 构造分页
        val pageNo = if (pageQuery.pageNo!! > 0) (pageQuery.pageNo!! - 1) else 0
        val pageSize = if (pageQuery.pageSize == null || pageQuery.pageSize!! <= 0) 20 else pageQuery.pageSize!!

        var count = mongoTemplate.count(query, clazz)
        query.limit(pageSize)
//        query.skip((pageQuery.pageNo!! * pageQuery.pageSize!!).toLong())
        query.skip((pageNo * pageSize).toLong())
        var list = mongoTemplate.find(query, clazz)

        val pageExtra = PageExtra(if(pageNo>0) pageNo else 1, pageSize, count)
        val pageList = PageList(list, pageExtra)
        return pageList
//        return Page<T>(list, total = count, pageNo = pageQuery.pageNo!!, pageSize = pageQuery.pageSize!!)
    }

    fun <T> cursor(clazz: Class<T>, mongoTemplate: MongoTemplate, cursor: CursorQuery): CursorList<T> {
        val query = Query()

        buildCriteria(clazz, query, cursor.filters)

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

            var criteria: Criteria
            when (cursor.direction) {
                1 -> {
                    criteria = Criteria.where(cursor.cursorSort!!.property).gt(cursor.cursor)
                }
                else -> {
                    criteria = Criteria.where(cursor.cursorSort!!.property).lt(cursor.cursor)
                }
            }
            query.addCriteria(criteria)
        }

        val cursorSort = cursor.cursorSort
        val sort: Sort
        when (cursorSort!!.type) {
            SortType.DSC -> sort = Sort.by(Sort.Direction.DESC, cursorSort.property)
            SortType.ASC -> sort = Sort.by(Sort.Direction.ASC, cursorSort.property)
            SortType.DEFAULT -> sort = Sort.by(Sort.DEFAULT_DIRECTION, cursorSort.property)
        }
        query.with(sort)
        query.limit(cursor.size!! + 1)

        var count = mongoTemplate.count(query, clazz)
        var list = mongoTemplate.find(query, clazz)
        var hasMore = false
        if (list.size >= 1 && list.size > cursor.size!!) {
            hasMore = true
            list.removeAt(cursor.size!!)
        }

        val cursorField = clazz.getDeclaredField(cursor.cursorSort!!.property)
        cursorField.isAccessible = true

        val minValue = if (list.isEmpty()) null else cursorField.get(list.first())
        val minCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(minValue!!)

        val maxValue = if (list.isEmpty()) null else cursorField.get(list.last())
        val maxCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(maxValue!!)

//        val cursorPage = CursorPage(hasMore, maxCursor, minCursor, list)
        val cursorExtra = CursorExtra(hasMore, minCursor, maxCursor, count)
        val cusorList = CursorList(list, cursorExtra)
        return cusorList
    }

}
