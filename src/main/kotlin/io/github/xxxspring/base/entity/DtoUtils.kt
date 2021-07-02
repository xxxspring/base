package io.github.xxxspring.base.entity

import io.github.xxxspring.base.dto.*


class DtoUtils {

    companion object {

        /**
         * @Deprecated
         */
        fun <T, S> toCursorPageDto(cursorPage: CursorPage<T>, transform: (T) -> S): CursorPageDto<S> {
            return CursorPageDto(
                    hasMore = cursorPage.hasMore,
                    maxCursor = cursorPage.maxCursor,
                    minCursor = cursorPage.minCursor,
                    data = cursorPage.data.map(transform)
            )
        }

        fun <T, S> toCursorListDto(cursorPage: CursorList<T>, transform: (T) -> S): CursorListDto<S> {
            return CursorListDto(
                    data = cursorPage.data!!.map(transform),
                    extra = CursorExtraDto(cursorPage.extra?.totalCount!!, cursorPage.extra?.hasMore!!, cursorPage.extra?.min?.toString()
                            ?: "", cursorPage.extra?.max?.toString() ?: "")
            )
        }

        /**
         * @Deprecated
         */
        fun fromCursorQueryDto(dto: CursorQueryDto): CursorQuery {
            return CursorQuery(
                    filters = dto.filters,
                    cursor = dto.cursor,
                    cursorSort = if (dto.cursorSort === null) null else fromSortSpecDto(dto.cursorSort!!),
                    size = dto.size,
                    direction = dto.direction
            )
        }

        fun <T, S> toPageDto(page: Page<T>, transform: (T) -> S): PageDto<S> {
            return PageDto(
                    total = page.total,
                    pageSize = page.pageSize,
                    pageNo = page.pageNo,
                    content = page.content.map(transform)
            )
        }

        fun <T, S> toPageListDto(page: PageList<T>, transform: (T) -> S): PageListDto<S> {
            return PageListDto(
                    data = page.data?.map(transform)!!,
                    extra = PageExtraDto(page.extra?.totalCount!!, page.extra?.pageNo!!, page.extra?.pageSize!!)
            )
        }

        fun fromPageQueryDto(dto: PageQueryDto): PageQuery {
            return PageQuery(
                    filters = dto.filters,
                    sort = dto.sort?.map { fromSortSpecDto(it) },
                    pageSize = dto.pageSize,
                    pageNo = dto.pageNo
            )
        }

        private fun fromSortSpecDto(dto: SortSpecDto): SortSpec {
            return SortSpec(
                    property = dto.property,
                    type = SortType.valueOf(dto.type),
                    ignoreCase = dto.ignoreCase
            )
        }
    }
}
