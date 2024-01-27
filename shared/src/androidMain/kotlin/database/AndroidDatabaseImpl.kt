package database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import id.harlabs.delight.gen.Database

class AndroidDatabaseImpl(private val context: Context): DatabaseFactory {

    override fun createDatabase(): Database {
        val sqlDriver = AndroidSqliteDriver(Database.Schema, context, "todo")
        return Database(sqlDriver)
    }
}