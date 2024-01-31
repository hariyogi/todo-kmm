package database.repo

import database.DesktopDatabaseConfig
import database.dto.CategoryDto
import database.dto.CategoryField
import database.mapper.CategoryDtoMapper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import utils.generateUUID
import java.time.LocalDateTime

class CategoryRepoImpl(private val config: DesktopDatabaseConfig): CategoryRepo {
    override suspend fun create(name: String, desc: String, color: String?): Deferred<String> {
        return config.jdbiExecutor.withHandle<String, RuntimeException> {handle ->
            val id = generateUUID()
            handle.createUpdate("""
                INSERT INTO category(
                    ${CategoryField.ID}, 
                    ${CategoryField.NAME}, 
                    ${CategoryField.DESC}, 
                    ${CategoryField.COLOR}, 
                    ${CategoryField.CREATED_AT}, 
                    ${CategoryField.UPDATED_AT}
                )
                VALUES (:id, :catName, :catDesc, :catColor, :createdAt, :updatedAt);
            """.trimIndent())
                .bind("id", id)
                .bind("catName", name)
                .bind("catColor", color)
                .bind("createdAt", LocalDateTime.now().toString())
                .bind("updatedAt", LocalDateTime.now().toString())

            id
        }.asDeferred()
    }

    override suspend fun findAll(): Deferred<List<CategoryDto>> {
        return config.jdbiExecutor.withHandle<List<CategoryDto>, RuntimeException> { handle ->
            handle.select("SELECT * FROM category")
                .map(CategoryDtoMapper())
                .list()
        }.asDeferred()
    }

    override suspend fun delete(id: String): Deferred<Int> {
        return config.jdbiExecutor.withHandle<Int, RuntimeException> { handle ->
            handle.createUpdate("DELETE FROM category WHERE ${CategoryField.ID} = :id")
                .bind("id", id)
                .execute()
        }.asDeferred()
    }
}