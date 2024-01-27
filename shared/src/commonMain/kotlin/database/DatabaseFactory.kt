package database

import id.harlabs.delight.gen.Database

interface DatabaseFactory {
    fun createDatabase(): Database
}