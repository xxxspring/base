package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteServiceClient<S, T> {

    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun delete(@PathVariable id: S): DataResponse<T>

}
