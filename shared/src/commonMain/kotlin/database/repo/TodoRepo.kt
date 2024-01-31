package database.repo

import database.dto.TodoDto
import kotlinx.coroutines.Deferred
import java.time.LocalDateTime

interface TodoRepo {
    suspend fun create(
        summary: String,
        description: String,
        checked: Boolean,
        startAt: LocalDateTime? = null,
        endAt: LocalDateTime? = null,
        catId: String? = null
    ): Deferred<String>
    suspend fun findAll(): Deferred<List<TodoDto>>
    suspend fun delete(id: String): Deferred<Int>
}