package database.repo

import database.dto.ConfigDto
import kotlinx.coroutines.Deferred

interface ConfigRepo {

    suspend fun createConfig(
        key: String,
        value: String
    ): Deferred<String>

    suspend fun findAll(): Deferred<List<ConfigDto>>

    suspend fun findById(key: String): Deferred<ConfigDto?>

    suspend fun delete(key: String): Deferred<Int>

    suspend fun update(key: String, value: String): Deferred<Int>
}