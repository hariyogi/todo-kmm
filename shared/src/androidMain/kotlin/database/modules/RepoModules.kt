package database.modules

import database.mobile.MobileTodoRepo
import database.repo.TodoRepo
import org.koin.dsl.module

val repoModules = module {
    single<TodoRepo> { MobileTodoRepo(get()) }
}