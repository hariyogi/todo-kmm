package database.dto

import id.harlabs.delight.gen.Todo
import utils.fromDbToBool
import utils.toLocalDateTime
import java.time.LocalDateTime

fun Todo.toTodoDto() = TodoDto(
    id = this.id,
    summary = this.summary,
    description = this.description,
    startAt = this.start_at.toLocalDateTime(),
    endAt = this.end_at.toLocalDateTime(),
    catId = this.cat_id,
    createdAt = LocalDateTime.parse(this.created_at),
    updatedAt = LocalDateTime.parse(this.updated_at),
    checked = this.checked.fromDbToBool()
)

data class TodoDto(
    val id: String,
    val summary: String,
    val description: String,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
    val catId: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val checked: Boolean
)