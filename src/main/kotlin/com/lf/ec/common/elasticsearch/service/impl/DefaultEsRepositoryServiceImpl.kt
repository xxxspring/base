package com.lf.ec.common.elasticsearch.service.impl

import com.lf.ec.common.base.service.CrudService
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.io.Serializable


open class DefaultEsRepositoryServiceImpl<K : Serializable?, T>(private val defaultRepository: ElasticsearchRepository<T, K>)
    : CrudEsRepositoryServiceImpl<K, T>(), CrudService<K, T> {

    override val repository: ElasticsearchRepository<T, K> get() = defaultRepository

}