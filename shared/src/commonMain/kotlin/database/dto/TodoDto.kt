package database.dto

import id.harlabs.delight.gen.Todo
import utils.fromDbToBool
import java.time.LocalDateTime

fun List<Todo>.toTodoDtoList() = this.map { it.toTodoDto() }

fun Todo.toTodoDto() = TodoDto(
    id = this.id,
    summary = this.summary,
    description = this.description,
    createdAt = LocalDateTime.parse(this.created_at),
    updatedAt = LocalDateTime.parse(this.updated_at),
    checked = this.checked.fromDbToBool()
)

data class TodoDto(
    val id: String,
    val summary: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val checked: Boolean
)