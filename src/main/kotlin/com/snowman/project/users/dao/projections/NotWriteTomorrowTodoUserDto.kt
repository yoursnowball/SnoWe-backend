package com.snowman.project.users.dao.projections

import com.querydsl.core.annotations.QueryProjection

class NotWriteTomorrowTodoUserDto @QueryProjection constructor(
    id: Long,
    fcmToken: String
) {
}