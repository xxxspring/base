package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

interface RetrieveServiceClient<S, T> {

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun retrieve(@PathVariable id: S): DataResponse<T>

}
