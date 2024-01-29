package database.repo

import database.dto.TodoDto
import kotlinx.coroutines.Deferred

interface TodoRepo {
    suspend fun create(summary: String, description: String, checked: Boolean): Deferred<String>
    suspend fun findAll(): Deferred<List<TodoDto>>
    suspend fun delete(id: String): Deferred<Int>
}