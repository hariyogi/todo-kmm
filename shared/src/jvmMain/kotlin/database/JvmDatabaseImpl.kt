package database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import id.harlabs.delight.gen.Database

class JvmDatabaseImpl: DatabaseFactory {
    override fun createDatabase(): Database {
        val sqlDriver = JdbcSqliteDriver("jdbc:sqlite:todo.db")
        return Database(sqlDriver)
    }
}