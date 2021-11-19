package com.snowman.project.utils.page

data class PageResponse<T>(
    val content: List<T>,
    val currentPage: Int,
    val isLast: Boolean
) {
}