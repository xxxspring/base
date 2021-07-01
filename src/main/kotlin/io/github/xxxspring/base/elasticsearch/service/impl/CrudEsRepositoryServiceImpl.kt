package io.github.xxxspring.base.elasticsearch.service.impl

import io.github.xxxspring.base.entity.CursorList
import io.github.xxxspring.base.entity.CursorQuery
import io.github.xxxspring.base.entity.PageList
import io.github.xxxspring.base.entity.PageQuery
import io.github.xxxspring.base.service.CrudService
import io.github.xxxspring.base.elasticsearch.util.EsUtil
import org.elasticsearch.client.RestHighLevelClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.io.Serializable
import java.lang.reflect.ParameterizedType

abstract class CrudEsRepositoryServiceImpl<K : Serializable?, T> : CrudService<K, T> {

    private val logger by lazy { LoggerFactory.getLogger(CrudEsRepositoryServiceImpl::class.java) }

    protected abstract val repository: ElasticsearchRepository<T, K>

    @Autowired
    lateinit var elasticsearchOperations: ElasticsearchOperations

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    override fun retrieve(id: K): T? {
        val result = repository.findById(id)
        return if (result.isPresent) result.get() else null
    }

    override fun batchRetrieve(ids: List<K>): List<T>? {
        return repository.findAllById(ids).toList()
    }

    override fun create(entity: T): Int {
        repository.save(entity)
        return 1
    }

    override fun update(entity: T): Int {
        repository.save(entity)
        return 1
    }

    override fun delete(id: K): Int {
        repository.deleteById(id)
        return 1
    }

    override fun batchDelete(ids: List<K>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun page(query: PageQuery): PageList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return EsUtil.page(clazz, elasticsearchOperations, query)
    }

    fun cursor2(cursorQuery: CursorQuery): CursorList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return EsUtil.cursor2(clazz, highLevelClient, cursorQuery)
    }

    override fun cursor(cursorQuery: CursorQuery): CursorList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return EsUtil.cursor(clazz, elasticsearchOperations, cursorQuery)
    }
}
