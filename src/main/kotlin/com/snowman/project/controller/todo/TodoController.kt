package com.snowman.project.controller.todo

import com.snowman.project.config.security.AuthInfo
import com.snowman.project.config.security.Authenticated
import com.snowman.project.controller.todo.req.SaveTodoRequest
import com.snowman.project.controller.todo.req.UpdateTodoRequest
import com.snowman.project.controller.todo.res.GetTodoResponse
import com.snowman.project.controller.todo.res.GetTodosResponse
import com.snowman.project.service.todo.TodoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/goals/{goalId}/todos")
class TodoController(
    val todoService: TodoService
) {
    @ApiOperation("투두리스트 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun makeTodos(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long,
        @RequestBody req: SaveTodoRequest
    ): GetTodosResponse {
        val userId = authInfo.id
        return GetTodosResponse(todoService.saveTodos(userId, goalId, req.todos))
    }

    @ApiOperation("투두 수정")
    @PutMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateTodo(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long,
        @PathVariable todoId: Long,
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

    @ApiOperation("투두 삭제")
    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTodo(
        @ApiIgnore
        @Authenticated authInfo: AuthInfo,
        @PathVariable goalId: Long,
        @PathVariable todoId: Long
    ) {
        val userId = authInfo.id
        todoService.deleteTodo(userId, goalId, todoId)
    }
}