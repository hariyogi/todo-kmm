package database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import id.harlabs.delight.gen.Database

class AndroidDatabaseImpl(private val context: Context) {

    fun createDatabase(): Database {
        val sqlDriver = AndroidSqliteDriver(Database.Schema, context, "harlabs_todo")
        return Database(sqlDriver)
    }
}