package com.snowman.project.dao.user.projections

import com.querydsl.core.annotations.QueryProjection

class NotWriteTomorrowTodoUserDto @QueryProjection constructor(
    id: Long,
    fcmToken: String
) {
}