package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.BaseResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody

interface BatchDeleteServiceClient<S> {

    @DeleteMapping(value = ["/batchDelete"], produces = ["application/json"])
    fun batchDelete(@RequestBody ids: List<S>): BaseResponse
}
