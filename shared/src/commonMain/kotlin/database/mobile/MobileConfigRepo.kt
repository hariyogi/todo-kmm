package database.mobile

import database.dto.ConfigDto
import database.dto.toDto
import database.repo.ConfigRepo
import id.harlabs.delight.gen.Database
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MobileConfigRepo(database: Database) : ConfigRepo {

    private val queries = database.configQueries

    override suspend fun createConfig(key: String, value: String): Deferred<String> =
        coroutineScope {
            async(Dispatchers.IO) {
                queries.insert(key, value)
                key
            }
        }

    override suspend fun findAll(): Deferred<List<ConfigDto>> = coroutineScope {
        async(Dispatchers.IO) {
            queries.findAll().executeAsList().map { it.toDto() }
        }
    }

    override suspend fun findById(key: String): Deferred<ConfigDto?> = coroutineScope {
        async(Dispatchers.IO) {
            queries.findById(key)
                .executeAsOneOrNull()
                ?.toDto()
        }
    }

    override suspend fun delete(key: String): Deferred<Int> = coroutineScope {
        async(Dispatchers.IO) {
            queries.deleteById(key)
            1
        }
    }

    override suspend fun update(key: String, value: String): Deferred<Int> = coroutineScope {
        async(Dispatchers.IO) {
            // Because sqldelight doesn't return row updated value
            // then we should check if key exist before updated
            val id = findById(key).await()
            if (id == null) {
                // to make it still consistent, we return 0 when key not found.
                0
            }else {
                queries.update(value, key)
                1
            }
        }
    }
}