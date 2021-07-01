package io.github.xxxspring.base.mongo.service.impl

import io.github.xxxspring.base.service.CrudService
import org.springframework.data.mongodb.repository.MongoRepository

open class DefaultMongoRepositoryServiceImpl<K, T>(private val defaultRepository: MongoRepository<T, K>)
    : CrudMongoRepositoryServiceImpl<K, T>(), CrudService<K, T> {

    override val repository: MongoRepository<T, K> get() = defaultRepository

}
