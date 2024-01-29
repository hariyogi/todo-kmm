package database.mobile

import database.dto.TodoDto
import database.dto.toTodoDto
import database.repo.TodoRepo
import id.harlabs.delight.gen.Database
import utils.generateUUID
import utils.toDbColumn
import java.time.LocalDateTime

class MobileTodoRepo(database: Database): TodoRepo {

    val todoQueries = database.todoQueries

    override fun create(summary: String, description: String, checked: Boolean): String {
        val id = generateUUID()
        todoQueries.insert(
            id = id,
            summary = summary,
            description = description,
            created_at = LocalDateTime.now().toString(),
            updated_at = LocalDateTime.now().toString(),
            checked = checked.toDbColumn()
        )

        return id
    }

    override fun findAll(): List<TodoDto> {
        return todoQueries.findAll().executeAsList().map { it.toTodoDto() }
    }
}