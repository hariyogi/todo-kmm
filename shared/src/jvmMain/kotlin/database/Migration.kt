package database

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.useHandleUnchecked
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import java.time.LocalDateTime

class Migration(private val jdbi: Jdbi) {

    companion object {
        private const val LATEST_VERSION = 3

        private val latestVersion = """
            SELECT level FROM migration_ver ORDER BY level DESC LIMIT 1;
        """.trimIndent()

        private val version0 = """
            CREATE TABLE IF NOT EXISTS migration_ver(
                level INTEGER NOT NULL PRIMARY KEY,
                created_at TEXT NOT NULL
            );
        """.trimIndent()

        private val version1 = """
            CREATE TABLE IF NOT EXISTS category(
                id text NOT NULL PRIMARY KEY,
                cat_name TEXT NOT NULL,
                cat_desc TEXT NOT NULL,
                cat_color TEXT NULL,
                created_at TEXT NOT NULL,
                updated_at TEXT NOT NULL
            );
        """.trimIndent()

        private val version2 = """
            PRAGMA foreign_keys = ON;
            
            CREATE TABLE IF NOT EXISTS todo(
                id text NOT NULL PRIMARY KEY,
                summary text NOT NULL,
                description text NOT NULL,
                start_at TEXT NULL,
                end_at TEXT NULL,
                created_at TEXT NOT NULL,
                updated_at TEXT NOT NULL,
                checked INTEGER NOT NULL DEFAULT 0,
                cat_id TEXT NULL,
                FOREIGN KEY (cat_id) REFERENCES category (id)
            );
        """.trimIndent()

        private val version3 = """
            CREATE TABLE config (
                key TEXT PRIMARY KEY NOT NULL,
                value_ TEXT NOT NULL
            );
        """.trimIndent()
    }

    fun migration() {

        jdbi.useHandleUnchecked { handle ->
            handle.createScript(version0).execute()
        }

        val version = jdbi.withHandleUnchecked { handle ->
            handle.select(latestVersion)
                .mapTo(Int::class.java)
                .findFirst()
                .orElse(null)
        } ?: 1

        if (version < LATEST_VERSION) {
            val migration = listOf(version1, version2, version3)
            jdbi.useHandleUnchecked { handle ->
                for (i in version..<migration.size) {
                    handle.createScript(migration[i]).execute()
                    handle.createUpdate("""
                        INSERT INTO migration_ver(level, created_at) 
                        values (:level, :created_at)"""
                        .trimIndent()
                    )
                        .bind("level", i + 1)
                        .bind("created_at", LocalDateTime.now().toString())
                        .execute()
                    println("Migration version ${i + 1} success")
                }
            }
        }else {
            println("Latest migration is $version already applied")
        }

    }
}