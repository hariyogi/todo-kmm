package viewmodel

import database.dto.TodoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel

class TodoViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TodoState())
    val uiState = _uiState.asStateFlow()

    fun cleanTodo() {
        _uiState.update {
            it.copy(
                todoList = listOf()
            )
        }
    }

    fun addTodo(todos: List<TodoDto>) {
        _uiState.update {
            it.copy(
                todoList = it.todoList + todos
            )
        }
    }

    fun deleteTodo(todo: TodoDto) {
        _uiState.update {
            it.copy(
                todoList = it.todoList - todo
            )
        }
    }
}