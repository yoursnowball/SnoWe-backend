package com.snowman.project.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/goals/{goalId}/todos")
class TodoController {

    /**
     * ToDoList 생성
     * List로 한번에 받는게 좋긴함
     */
    @PostMapping
    fun makeTodos(@PathVariable goalId: Long) {

    }

    /**
     * ToDo 업데이트
     * 이름, 완료 여부 등등
     */
    @PutMapping("/{todoId}")
    fun updateTodo(
        @PathVariable goalId: Long,
        @PathVariable todoId: Long
    ) {

    }

    /**
     * ToDo 삭제
     */
    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable goalId: Long,
        @PathVariable todoId: Long
    ) {

    }
}