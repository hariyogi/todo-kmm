package viewmodel

import database.dto.TodoDto

data class TodoState(
    val todoList: List<TodoDto> = listOf()
)
