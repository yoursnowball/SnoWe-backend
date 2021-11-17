package com.snowman.project.controller.todo

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.todo.req.SaveTodoRequest
import com.snowman.project.controller.todo.req.UpdateTodoRequest
import com.snowman.project.controller.todo.res.GetTodoResponse
import com.snowman.project.controller.todo.res.GetTodosResponse
import com.snowman.project.service.todo.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/goals/{goalId}/todos")
class TodoController(
        val todoService: TodoService
) {
    /**
     * ToDoList 생성
     * List로 한번에 받는게 좋긴함
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun makeTodos(
            @PathVariable goalId: Long,
            @Authenticated authInfo: AuthInfo,
            @RequestBody req: SaveTodoRequest
    ): GetTodosResponse {
        val userId = authInfo.id
        return GetTodosResponse(todoService.saveTodos(userId, goalId, req.todos))
    }

    /**
     * ToDo 업데이트
     * 이름, 완료 여부 등등
     */
    @PutMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateTodo(
            @PathVariable goalId: Long,
            @PathVariable todoId: Long,
            @Authenticated authInfo: AuthInfo,
            @Valid @RequestBody req: UpdateTodoRequest
    ): GetTodoResponse {
        val userId = authInfo.id
        return GetTodoResponse(
                todoService.updateToDo(
                        userId = userId,
                        goalId = goalId,
                        todoId = todoId,
                        name = req.name,
                        succeed = req.succeed
                )
        )
    }

    /**
     * ToDo 삭제
     */
    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTodo(
            @PathVariable goalId: Long,
            @PathVariable todoId: Long,
            @Authenticated authInfo: AuthInfo
    ) {
        val userId = authInfo.id
        todoService.deleteTodo(userId, goalId, todoId)
    }
}