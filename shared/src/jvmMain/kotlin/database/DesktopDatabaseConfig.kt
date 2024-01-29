package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.async.JdbiExecutor
import org.jdbi.v3.core.kotlin.useHandleUnchecked
import java.util.concurrent.Executors

class DesktopDatabaseConfig {

    companion object {
        const val DATABASE_NAME = "todo.db"
        const val DATABASE_PATH = "."
        const val POOL_NAME = "TodoPool"
        const val MAXIMUM_POOL_SIZE = 50
    }

    val jdbi: Jdbi
    val jdbiExecutor: JdbiExecutor

    init {
        jdbi = buildJdbi()
        val executor = Executors.newFixedThreadPool(MAXIMUM_POOL_SIZE)
        jdbiExecutor = JdbiExecutor.create(jdbi, executor)
        initTable()
    }

    private fun initTable() {
        jdbi.useHandleUnchecked { handle ->
            handle.createUpdate("""
                CREATE TABLE IF NOT EXISTS todo(
                    id text NOT NULL PRIMARY KEY,
                    summary text NOT NULL,
                    description text NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL,
                    checked INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent())
                .execute()
        }
    }

    private fun buildJdbi(): Jdbi {
        val dataSource = buildDatasource()
        return Jdbi.create(dataSource)
    }

    private fun buildDatasource(): HikariDataSource {
        val config = HikariConfig()
        config.poolName = POOL_NAME
        config.driverClassName = "org.sqlite.JDBC"
        config.jdbcUrl = "jdbc:sqlite:$DATABASE_PATH/$DATABASE_NAME"
        config.connectionTestQuery = "SELECT 1"
        config.maxLifetime = 60000
        config.idleTimeout = 45000
        config.maximumPoolSize = MAXIMUM_POOL_SIZE
        return HikariDataSource(config)
    }
}