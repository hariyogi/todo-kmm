package database.mobile

import database.dto.TodoDto
import database.dto.toTodoDto
import database.repo.TodoRepo
import id.harlabs.delight.gen.Database
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import utils.generateUUID
import utils.toDbColumn
import java.time.LocalDateTime

class MobileTodoRepo(database: Database) : TodoRepo {

    private val todoQueries = database.todoQueries

    override suspend fun create(
        summary: String,
        description: String,
        checked: Boolean
    ): Deferred<String> = coroutineScope {
        async(Dispatchers.IO) {
            val id = generateUUID()
            todoQueries.insert(
                id = id,
                summary = summary,
                description = description,
                created_at = LocalDateTime.now().toString(),
                updated_at = LocalDateTime.now().toString(),
                checked = checked.toDbColumn()
            )

            id
        }
    }

    override suspend fun findAll(): Deferred<List<TodoDto>> = coroutineScope {
        async(Dispatchers.IO) {
            todoQueries.findAll().executeAsList().map { it.toTodoDto() }
        }
    }

    override suspend fun delete(id: String): Deferred<Int> {
        return coroutineScope {
            async(Dispatchers.IO) {
                todoQueries.delete(id)
                1
            }
        }
    }
}