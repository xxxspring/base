package io.github.xxxspring.base.client

import io.github.xxxspring.base.dto.DataResponse
import io.github.xxxspring.base.dto.PageDto
import io.github.xxxspring.base.dto.PageListDto
import io.github.xxxspring.base.dto.PageQueryDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface PageServiceClient<T> {

    @PostMapping(value = ["/page"], produces = ["application/json"])
    fun page(@RequestBody queryDto: PageQueryDto): DataResponse<PageListDto<T>>
}
