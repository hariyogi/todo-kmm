package modules

import database.DesktopDatabaseConfig
import org.koin.dsl.module

val databaseConfModule = module {
    single { DesktopDatabaseConfig() }
}