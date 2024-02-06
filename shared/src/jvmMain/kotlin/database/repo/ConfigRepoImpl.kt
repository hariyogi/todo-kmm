package database.repo

import database.DesktopDatabaseConfig
import database.dto.ConfigDto
import database.dto.ConfigField
import database.mapper.ConfigDtoMapper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred

class ConfigRepoImpl(private val config: DesktopDatabaseConfig) : ConfigRepo {
    override suspend fun createConfig(key: String, value: String): Deferred<String> {
        return config.jdbiExecutor.withHandle<String, RuntimeException> { handle ->
            handle.createUpdate("""
                INSERT INTO ${ConfigField.TABLE}(${ConfigField.KEY}, ${ConfigField.VALUE})
                VALUES (:key, :value)
            """.trimIndent())
                .bind("key", key)
                .bind("value", value)
                .execute()
            key
        }.asDeferred()
    }

    override suspend fun findAll(): Deferred<List<ConfigDto>> {
        return config.jdbiExecutor.withHandle<List<ConfigDto>, RuntimeException> { handle ->
            handle.select("SELECT * FROM ${ConfigField.TABLE}")
                .map(ConfigDtoMapper())
                .list()
        }.asDeferred()
    }

    override suspend fun findById(key: String): Deferred<ConfigDto?> {
        return config.jdbiExecutor.withHandle<ConfigDto, RuntimeException> { handle ->
            handle.select("SELECT * FROM ${ConfigField.TABLE} WHERE ${ConfigField.KEY}")
                .map(ConfigDtoMapper())
                .findFirst()
                .orElse(null)
        }.asDeferred()
    }

    override suspend fun delete(key: String): Deferred<Int> {
        return config.jdbiExecutor.withHandle<Int, RuntimeException> { handle ->
            handle.createUpdate("DELETE FROM ${ConfigField.TABLE} WHERE ${ConfigField.KEY} = :key")
                .bind("key", key)
                .execute()
        }.asDeferred()
    }

    override suspend fun update(key: String, value: String): Deferred<Int> {
        return config.jdbiExecutor.withHandle<Int, RuntimeException> { handle ->
            handle.createUpdate("""
                UPDATE ${ConfigField.TABLE}
                SET ${ConfigField.VALUE} = :value
                WHERE ${ConfigField.KEY} = :key
            """.trimIndent())
                .bind("key", key)
                .bind("value", value)
                .execute()
        }.asDeferred()
    }
}