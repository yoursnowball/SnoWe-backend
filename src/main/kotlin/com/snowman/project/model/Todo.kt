package com.snowman.project.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    val id: Long? = null
)
