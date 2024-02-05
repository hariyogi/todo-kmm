package database.mobile

import database.dto.CategoryDto
import database.dto.toDto
import database.repo.CategoryRepo
import id.harlabs.delight.gen.Database
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import utils.generateUUID
import java.time.LocalDateTime

class MobileCategoryRepo(database: Database) : CategoryRepo {

    private val queries = database.categoryQueries

    override suspend fun create(name: String, desc: String, color: String?): Deferred<String> =
        coroutineScope {
            async(Dispatchers.IO) {
                val id = generateUUID()
                queries.insert(
                    id = id,
                    cat_name = name,
                    cat_desc = desc,
                    cat_color = color,
                    created_at = LocalDateTime.now().toString(),
                    updated_at = LocalDateTime.now().toString()
                )
                id
            }
        }

    override suspend fun findAll(): Deferred<List<CategoryDto>> = coroutineScope {
        async(Dispatchers.IO) {
            queries.findAll().executeAsList().map { it.toDto() }
        }
    }

    override suspend fun delete(id: String): Deferred<Int> = coroutineScope {
        async(Dispatchers.IO) {
            queries.deleteById(id)
            1
        }
    }

    override suspend fun findById(id: String): Deferred<CategoryDto?> = coroutineScope {
        async(Dispatchers.IO) {
            queries.findById(id)
                .executeAsOneOrNull()
                ?.toDto()
        }
    }
}