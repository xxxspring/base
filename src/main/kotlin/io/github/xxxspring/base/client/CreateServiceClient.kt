package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface CreateServiceClient<S, T> {

    @PostMapping(value = ["/create"], produces = ["application/json"])
    fun create(@RequestBody dto: S): DataResponse<T>
}
