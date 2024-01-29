package modules

import database.DesktopDatabaseConfig
import org.jdbi.v3.core.Jdbi
import org.koin.dsl.module

val databaseConfModule = module {
    single<Jdbi> { DesktopDatabaseConfig().jdbi }
}