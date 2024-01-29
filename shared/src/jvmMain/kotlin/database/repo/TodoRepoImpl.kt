package database.repo

import database.dto.TodoDto
import database.mapper.TodoDtoMapper
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.useHandleUnchecked
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import utils.generateUUID
import utils.toDbColumn
import java.time.LocalDateTime

class TodoRepoImpl(private val jdbi: Jdbi): TodoRepo {
    override fun create(summary: String, description: String, checked: Boolean): String {
        val id = generateUUID()
        val now = LocalDateTime.now().toString()

        jdbi.useHandleUnchecked { handle ->
            handle.createUpdate("""
                INSERT INTO todo(id, summary, description, created_at, updated_at, checked)
                VALUES(:id, :summary, :description, :createdAt, :updatedAt, :checked)
            """.trimIndent())
                .bind("id", id)
                .bind("summary", summary)
                .bind("description", description)
                .bind("createdAt", now)
                .bind("updatedAt", now)
                .bind("checked", checked.toDbColumn())
                .execute()
        }

        return id
    }

    override fun findAll(): List<TodoDto> {
        return jdbi.withHandleUnchecked {handle ->
            handle.select("SELECT * FROM todo")
                .map(TodoDtoMapper())
                .list()
        }
    }
}