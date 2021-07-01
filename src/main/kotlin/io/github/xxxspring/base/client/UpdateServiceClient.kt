package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface UpdateServiceClient<S, T> {

    @PostMapping(value = ["/update"], produces = ["application/json"])
    fun update(@RequestBody dto: S): DataResponse<T>
}
