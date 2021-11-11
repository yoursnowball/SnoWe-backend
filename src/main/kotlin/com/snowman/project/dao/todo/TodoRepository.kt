package com.snowman.project.dao.todo

import com.snowman.project.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long>, TodoRepositoryCustom {
}