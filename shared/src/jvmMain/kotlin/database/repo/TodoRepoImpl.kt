package database.repo

import database.DesktopDatabaseConfig
import database.dto.TodoDto
import database.mapper.TodoDtoMapper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import utils.generateUUID
import utils.toDbColumn
import java.time.LocalDateTime

class TodoRepoImpl(private val db: DesktopDatabaseConfig) : TodoRepo {
    override suspend fun create(
        summary: String,
        description: String,
        checked: Boolean
    ): Deferred<String> {
        val id = generateUUID()
        val now = LocalDateTime.now().toString()

        return db.jdbiExecutor.withHandle<String, RuntimeException> { handle ->
            handle.createUpdate(
                """
                INSERT INTO todo(id, summary, description, created_at, updated_at, checked)
                VALUES(:id, :summary, :description, :createdAt, :updatedAt, :checked)
            """.trimIndent()
            )
                .bind("id", id)
                .bind("summary", summary)
                .bind("description", description)
                .bind("createdAt", now)
                .bind("updatedAt", now)
                .bind("checked", checked.toDbColumn())
                .execute()

            id
        }
            .asDeferred()
    }

    override suspend fun findAll(): Deferred<List<TodoDto>> {
        return db.jdbiExecutor.withHandle<List<TodoDto>, RuntimeException> { handle ->
            handle.select("SELECT * FROM todo")
                .map(TodoDtoMapper())
                .list()
        }.asDeferred()
    }

    override suspend fun delete(id: String): Deferred<Int> {
        return db.jdbiExecutor.withHandle<Int, IllegalArgumentException> { handle ->
            handle.createUpdate("DELETE FROM todo WHERE id = :id")
                .bind("id", id)
                .execute()
        }.asDeferred()
    }

}