package com.snowman.project.model.push.enums

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