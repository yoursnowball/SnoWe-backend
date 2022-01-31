package com.snowman.project.notifications.domain.enums

import org.codehaus.jackson.annotate.JsonIgnore

enum class PushType {

    LEVELUP,
    @JsonIgnore
    ALLCLEAR,
    CHEERUP,
    @JsonIgnore
    WRITE,
    DAILY
}