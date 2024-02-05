package viewmodel

import database.dto.TodoDto

data class MainScreenState(
    val todoList: List<TodoDto> = listOf(),
    val showTopBar: Boolean = true
)
