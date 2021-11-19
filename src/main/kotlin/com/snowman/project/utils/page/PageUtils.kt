package com.snowman.project.utils.page

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PageUtils {
    companion object {
        const val pageScale = 10

        fun of(page: Int): PageRequest {
            return PageRequest.of(page, pageScale)
        }

        fun of(page: Int, sort: Sort): PageRequest {
            return PageRequest.of(page, pageScale, sort)
        }
    }
}