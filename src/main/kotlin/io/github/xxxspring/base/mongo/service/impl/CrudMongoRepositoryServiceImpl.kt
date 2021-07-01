package io.github.xxxspring.base.mongo.service.impl

import io.github.xxxspring.base.entity.*
import io.github.xxxspring.base.service.CrudService
import io.github.xxxspring.base.mongo.util.MongoUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import java.lang.reflect.ParameterizedType

abstract class CrudMongoRepositoryServiceImpl<K, T> : CrudService<K, T> {

    protected abstract val repository: MongoRepository<T, K>

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    override fun retrieve(id: K): T? {
        val result = repository.findById(id)
        return if (result.isPresent) result.get() else null
    }

    override fun batchRetrieve(ids: List<K>): List<T>? {
        return repository.findAllById(ids).toList()
    }

    override fun create(entity: T): Int {
        repository.insert(entity)
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
        return MongoUtil.page(clazz, mongoTemplate, query)
    }

    override fun cursor(cursorQuery: CursorQuery): CursorList<T> {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        return MongoUtil.cursor(clazz, mongoTemplate, cursorQuery)
    }
}
