package com.snowman.project.common.utils.page

data class PageResponse<T>(
    val content: List<T>,
    val currentPage: Int,
    val isLast: Boolean
) {
}