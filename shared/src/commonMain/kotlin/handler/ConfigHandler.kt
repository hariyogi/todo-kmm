package handler

import database.repo.ConfigRepo

/**
 * Handling application configuration
 */
class ConfigHandler(
    private val configRepo: ConfigRepo
) {

    /**
     * Update config by key. When there is no key exists in database,
     * it will create new config by key.
     */
    suspend fun updateConfig(key: String, value: String) {
        val updatedRow = configRepo.update(key, value).await()

        if (updatedRow <= 0) {
            configRepo.createConfig(key, value).await()
        }
    }

    suspend fun findAllConfig() = configRepo.findAll()

}