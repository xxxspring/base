package io.github.xxxspring.base.elasticsearch.util

import com.alibaba.fastjson.JSONObject
import io.github.xxxspring.base.entity.*
import io.github.xxxspring.base.util.CursorTypeUtil
import org.apache.lucene.search.join.ScoreMode
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.ScoreSortBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortOrder
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import java.lang.reflect.Field
import java.util.*
import kotlin.streams.toList


object EsUtil {

    private val logger by lazy { LoggerFactory.getLogger(EsUtil::class.java) }

    private val classFieldMap = mutableMapOf<Class<*>, MutableMap<String, Field>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> buildCriteria(clazz: Class<T>, query: BoolQueryBuilder, filters: Map<String, Any>?) {

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
//                    val subQuery = QueryBuilders.boolQuery()
                    subFilters.forEach { subFilter ->
                        val subQuery = QueryBuilders.boolQuery()
                        buildCriteria(clazz, subQuery, subFilter)
                        query.must(subQuery)
                    }
//                    query.must(subQuery)
                }
                FilterType.OR.name -> {
                    val subFilters = filter.value as List<Map<String, Any>>
//                    val subQuery = QueryBuilders.boolQuery()
                    subFilters.forEach { subFilter ->
                        val subQuery = QueryBuilders.boolQuery()
                        buildCriteria(clazz, subQuery, subFilter)
                        query.should(subQuery)
                    }
                    // 有should 至少匹配1个
                    query.minimumShouldMatch(1)
//                    query.should(subQuery)
                }
                FilterType.NOR.name -> {
                    val subFilters = filter.value as List<Map<String, Any>>
                    val subQuery = QueryBuilders.boolQuery()
                    subFilters.forEach { subFilter ->
                        buildCriteria(clazz, subQuery, subFilter)
                    }
                    query.mustNot(subQuery)
                }
                else -> {   // property
                    val property = filter.key
                    val vMap = filter.value as Map<String, Any>

                    vMap.forEach {
                        var value: Any = it.value
                        val field = fieldMap[property]
                        /* 此处将时间全部转换为时间戳，java传入查询参数，对于
                         **ES中，index的mapping设置的时间字段的格式是不能解析的
                         */
                        if (field != null) {
                            if (field.type == Date::class.java) {
                                if (value is String) {
//                                    value = Date.parse(value)
                                    value = (Date.parse(value) as Date).time
                                } else if (value is Number) {
//                                    value = Date(value.toLong())
                                    value = value.toLong()
                                }
                            }
                        }

                        when (it.key) {
                            FilterType.EQ.name -> query.must(QueryBuilders.matchQuery(property, value))
                            FilterType.EQ_SCORE.name -> query.must(
                                    QueryBuilders.functionScoreQuery(
                                            QueryBuilders.matchQuery(
                                                    property, (value as List<Any>)[0]),
                                            ScoreFunctionBuilders.weightFactorFunction(((value as List<Any>)[1].toString()).toFloat())
                                    )
                                            .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                                            .setMinScore(2.0F)
                            )
                            FilterType.GT.name -> query.must(QueryBuilders.rangeQuery(property).from(value, false))
                            FilterType.GT_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).from(value, false))
                            FilterType.GTE.name -> query.must(QueryBuilders.rangeQuery(property).from(value, true))
                            FilterType.GTE_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).from(value, true))
                            FilterType.LT.name -> query.must(QueryBuilders.rangeQuery(property).to(value, false))
                            FilterType.LT_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).to(value, false))
                            FilterType.LTE.name -> query.must(QueryBuilders.rangeQuery(property).to(value, true))
                            FilterType.LTE_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).to(value, true))
                            FilterType.RANGE.name -> query.must(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], true).to((value as List<Any>)[1], true))
                            FilterType.RANGE_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], true).to((value as List<Any>)[1], true))
                            FilterType.RANGEL.name -> query.must(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], true).to((value as List<Any>)[1], false))
                            FilterType.RANGEL_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], true).to((value as List<Any>)[1], false))
                            FilterType.RANGER.name -> query.must(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], false).to((value as List<Any>)[1], true))
                            FilterType.RANGER_FILTER.name -> query.filter(QueryBuilders.rangeQuery(property).from((value as List<Any>)[0], false).to((value as List<Any>)[1], true))
                            FilterType.LIKE.name -> query.must(QueryBuilders.fuzzyQuery(property, value))
                            FilterType.IN.name -> query.must(QueryBuilders.termsQuery(property, value as List<Any>))
                            FilterType.NOT_IN.name -> query.mustNot(QueryBuilders.termsQuery(property, value))
                            FilterType.NE.name -> query.mustNot(QueryBuilders.matchQuery(property, value))
                            FilterType.NOT_LIKE.name -> query.mustNot(QueryBuilders.fuzzyQuery(property, value))
                            FilterType.MATCH.name -> query.must(QueryBuilders.matchQuery(property, value))
                            FilterType.MULTI_MATCH.name -> query.must(QueryBuilders.multiMatchQuery(property, value.toString()))
                            FilterType.TERM.name -> query.must(QueryBuilders.termQuery(property, value))
                            FilterType.TERM_FILTER.name -> query.filter(QueryBuilders.termQuery(property, value))
                            FilterType.TERMS.name -> query.must(QueryBuilders.termsQuery(property, value as List<Any>))
                            FilterType.TERMS_FILTER.name -> query.filter(QueryBuilders.termsQuery(property, value as List<Any>))
                            FilterType.NESTED.name -> {
                                // 嵌套查询结构
/*                              mapOf("path" to mapOf(
                                    "NESTED" to mapOf<String, Any>()
                                ))*/
                                val subFilter = it.value as Map<String, Any>
                                val subQuery = QueryBuilders.boolQuery()
                                buildCriteria(clazz, subQuery, subFilter)
                                val nestQuery = QueryBuilders.nestedQuery(property, subQuery, ScoreMode.None)
                                query.must(nestQuery)
                            }
                        }
                    }
                }
            }
        }

        logger.info("buildCriteria, query: " + query.toString())
    }

    fun <T> page(clazz: Class<T>, elasticsearchOperations: ElasticsearchOperations, pageQuery: PageQuery): PageList<T> {

        // 构造查询builder  NativeSearchQueryBuilder
        val searchQueryBuilder = NativeSearchQueryBuilder()

        // 构造查询
        val boolQuery = QueryBuilders.boolQuery()
        buildCriteria(clazz, boolQuery, pageQuery.filters)
        searchQueryBuilder.withQuery(boolQuery)
//        searchQueryBuilder.withMinScore(1.0F)

        // 构造排序
        val pageSort = pageQuery.sort
        pageSort?.forEach {
            var sort = when (it!!.type) {
                SortType.DSC -> SortBuilders.fieldSort(it.property).order(SortOrder.DESC)
                SortType.ASC -> SortBuilders.fieldSort(it.property).order(SortOrder.ASC)
                SortType.DEFAULT -> SortBuilders.fieldSort(it.property).order(SortOrder.DESC)
            }
            searchQueryBuilder.withSort(sort)
        }

        searchQueryBuilder.withSort(ScoreSortBuilder())

        // 总数
        var count = pageCount(clazz, searchQueryBuilder, elasticsearchOperations, pageQuery)

        // 构造分页
        val pageNo = if (pageQuery.pageNo!! > 0) (pageQuery.pageNo!! - 1) else 0
        val pageSize = if (pageQuery.pageSize == null || pageQuery.pageSize!! <= 0) 20 else pageQuery.pageSize!!
        val pageable: Pageable = PageRequest.of(pageNo!!, pageSize)
        searchQueryBuilder.withPageable(pageable)

        // 查询
        val searchQuery = searchQueryBuilder.build()
//        setIndex(searchQuery, clazz, pageQuery)
        val list = elasticsearchOperations.queryForPage<T>(searchQuery, clazz).get().toList()
        val pageExtra = PageExtra(pageQuery.pageNo!!, pageSize, count)
        val pageList = PageList(list, pageExtra)
        return pageList
    }

    fun <T> pageCount(clazz: Class<T>, searchQueryBuilder: NativeSearchQueryBuilder, elasticsearchOperations: ElasticsearchOperations, pageQuery: PageQuery): Long {
        val searchQuery = searchQueryBuilder.build()
//        setIndex(searchQuery, clazz, pageQuery)
        val scroll = elasticsearchOperations.startScroll(3000, searchQuery, clazz)
        val count = scroll.totalElements
        return count
    }

    fun <T> setIndex(searchQuery: NativeSearchQuery, clazz: Class<T>, pageQuery: PageQuery) {
        if (clazz.isAnnotationPresent(Document::class.java)) {
            val document = clazz.getAnnotation(Document::class.java)
            var indexName = document.indexName

//            if(pageQuery.index != null){
//                indexName = (indexName + "_" +pageQuery.index)
//                searchQuery.addIndices(indexName)
//            }

//            logger.error("------------------- indexName: ${indexName}")

        }
    }

    fun <T> cursor2(clazz: Class<T>, client: RestHighLevelClient, cursor: CursorQuery): CursorList<T> {
        // 构造查询builder  NativeSearchQueryBuilder
        val searchRequest = SearchRequest()
        val searchSourceBuilder = SearchSourceBuilder()

        // 构造查询
        val boolQuery = QueryBuilders.boolQuery()
        buildCriteria(clazz, boolQuery, cursor.filters)

        // 加入游标位置
        if (cursor.cursor !== null) {
//            searchSourceBuilder.from(cursor.cursor.toString().toInt())

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
                    boolQuery.filter(QueryBuilders.rangeQuery(cursor.cursorSort!!.property).from(cursor.cursor, false))
                }
                else -> {
                    boolQuery.filter(QueryBuilders.rangeQuery(cursor.cursorSort!!.property).to(cursor.cursor, false))
                }
            }
        }
        //综合查询条件
        searchSourceBuilder.query(boolQuery)

        logger.debug("cursor query: ${searchSourceBuilder.query()}")

        // 排序
        var isDescned = false       // 降序排列
        val cursorSort = cursor.cursorSort
        val sort = when (cursorSort!!.type) {
            SortType.DSC -> {
                isDescned = true
                SortBuilders.fieldSort(cursorSort.property).order(SortOrder.DESC)
            }
            SortType.ASC -> SortBuilders.fieldSort(cursorSort.property).order(SortOrder.ASC)
            SortType.DEFAULT -> {
                isDescned = true
                SortBuilders.fieldSort(cursorSort.property).order(SortOrder.DESC)
            }
        }

        if (sort != null) {
            searchSourceBuilder.sort(sort)
        }
        searchSourceBuilder.size(cursor.size!!)
        searchSourceBuilder.trackTotalHits(true)
        // 转换语句
        searchRequest.source(searchSourceBuilder)

        // 指定索引
        val document = clazz.getAnnotation(Document::class.java)
        var indexName = document.indexName
        searchRequest.indices(indexName)

        logger.info("cursor query: ${searchSourceBuilder}")
        // 执行查询
        val searchResponse = client.search(searchRequest, RequestOptions.DEFAULT)
        var hits = searchResponse.getHits().hits.toMutableList()

        var count = searchResponse.hits.totalHits
        var hasMore = false
        if (hits.size >= 1 && count > cursor.size!!) {
            hasMore = true
        }

        val list = mutableListOf<T>()
        hits.map {
            val str = it.sourceAsString!!
            val pojo = JSONObject.parseObject(str, clazz)!!
            list.add(pojo)
        }

        val cursorField = clazz.getDeclaredField(cursor.cursorSort!!.property)
        cursorField.isAccessible = true

        val minValue = if (list.isEmpty()) null else cursorField.get((if (isDescned) list.last() else list.first()))
        val minCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(minValue!!)

        val maxValue = if (list.isEmpty()) null else cursorField.get((if (isDescned) list.first() else list.last()))
        val maxCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(maxValue!!)

        val cursorExtra = CursorExtra(hasMore, minCursor, maxCursor, count)
        val cusorList = CursorList(list, cursorExtra)
        return cusorList
    }

    fun <T> cursor(clazz: Class<T>, elasticsearchOperations: ElasticsearchOperations, cursor: CursorQuery): CursorList<T> {
        // 构造查询builder  NativeSearchQueryBuilder
        val searchQueryBuilder = NativeSearchQueryBuilder()

        // 构造查询
        val boolQuery = QueryBuilders.boolQuery()
        buildCriteria(clazz, boolQuery, cursor.filters)


        // 加入游标位置
        if (cursor.cursor !== null) {
//            searchSourceBuilder.from(cursor.cursor.toString().toInt())

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
                    boolQuery.filter(QueryBuilders.rangeQuery(cursor.cursorSort!!.property).from(cursor.cursor, false))
                }
                else -> {
                    boolQuery.filter(QueryBuilders.rangeQuery(cursor.cursorSort!!.property).to(cursor.cursor, false))
                }
            }
        }
        //综合查询条件
        searchQueryBuilder.withQuery(boolQuery)

        // 排序
        var isDescned = false       // 降序排列
        val cursorSort = cursor.cursorSort
        val sort = when (cursorSort!!.type) {
            SortType.DSC -> {
                isDescned = true
                SortBuilders.fieldSort(cursorSort.property).order(SortOrder.DESC)
            }
            SortType.ASC -> SortBuilders.fieldSort(cursorSort.property).order(SortOrder.ASC)
            SortType.DEFAULT -> {
                isDescned = true
                SortBuilders.fieldSort(cursorSort.property).order(SortOrder.DESC)
            }
        }

        if (sort != null) {
            searchQueryBuilder.withSort(sort)
        }

        // 构造分页
        val pageNo = 0
        val pageSize = if (cursor.size == null || cursor.size!! <= 0) 20 else cursor.size!!
        val pageable: Pageable = PageRequest.of(pageNo!!, pageSize)
        searchQueryBuilder.withPageable(pageable)

        // 转换语句
        val searchQuery = searchQueryBuilder.build()
        logger.info("cursor query: ${searchQuery.query}")

        // 执行查询
//        val searchResponse = elasticsearchTemplate.startScroll(3000, searchQuery, clazz)
        val searchResponse = elasticsearchOperations.queryForPage(searchQuery, clazz)
        var list = searchResponse.content

        var count = searchResponse.totalElements
        var hasMore = false
        if (list.size >= 1 && count > cursor.size!!) {
            hasMore = true
        }

        val cursorField = clazz.getDeclaredField(cursor.cursorSort!!.property)
        cursorField.isAccessible = true

        val minValue = if (list.isEmpty()) null else cursorField.get((if (isDescned) list.last() else list.first()))
        val minCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(minValue!!)

        val maxValue = if (list.isEmpty()) null else cursorField.get((if (isDescned) list.first() else list.last()))
        val maxCursor = if (list.isEmpty()) null else CursorTypeUtil.transformType(maxValue!!)

        val cursorExtra = CursorExtra(hasMore, minCursor, maxCursor, count)
        val cusorList = CursorList(list, cursorExtra)
        return cusorList
    }


}
