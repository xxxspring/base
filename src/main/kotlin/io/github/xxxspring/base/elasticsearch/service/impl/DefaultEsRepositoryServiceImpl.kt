package io.github.xxxspring.base.elasticsearch.service.impl

import io.github.xxxspring.base.service.CrudService
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.io.Serializable


open class DefaultEsRepositoryServiceImpl<K : Serializable?, T>(private val defaultRepository: ElasticsearchRepository<T, K>)
    : CrudEsRepositoryServiceImpl<K, T>(), CrudService<K, T> {

    override val repository: ElasticsearchRepository<T, K> get() = defaultRepository

}
