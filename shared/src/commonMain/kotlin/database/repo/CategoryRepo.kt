package database.repo

import database.dto.CategoryDto
import kotlinx.coroutines.Deferred

interface CategoryRepo {

    suspend fun create(
        name: String,
        desc: String,
        color: String? = null
    ): Deferred<String>

    suspend fun findAll(): Deferred<List<CategoryDto>>

    suspend fun delete(id: String): Deferred<Int>
}