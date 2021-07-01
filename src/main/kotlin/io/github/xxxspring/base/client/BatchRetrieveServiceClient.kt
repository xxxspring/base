package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface BatchRetrieveServiceClient<S, T> {

    @PostMapping(value = ["/batchRetrieve"], produces = ["application/json"])
    fun batchRetrieve(@RequestBody ids: List<S>): DataResponse<List<T>>
}
