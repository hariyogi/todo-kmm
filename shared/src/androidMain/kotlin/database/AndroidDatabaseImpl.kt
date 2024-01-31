package database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import id.harlabs.delight.gen.Database

class AndroidDatabaseImpl(private val context: Context) {

    fun createDatabase(): Database {
        val sqlDriver = AndroidSqliteDriver(
            schema = Database.Schema, context,
            name = "harlabs_todo",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
        return Database(sqlDriver)
    }
}