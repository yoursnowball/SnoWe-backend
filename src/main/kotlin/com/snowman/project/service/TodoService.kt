package com.snowman.project.service

import com.snowman.project.dao.TodoRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TodoService(val todoRepository: TodoRepository) {
}