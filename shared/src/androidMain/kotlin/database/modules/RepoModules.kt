package database.modules

import database.mobile.MobileCategoryRepo
import database.mobile.MobileConfigRepo
import database.mobile.MobileTodoRepo
import database.repo.CategoryRepo
import database.repo.ConfigRepo
import database.repo.TodoRepo
import org.koin.dsl.module

val repoModules = module {
    single<TodoRepo> { MobileTodoRepo(get()) }
    single<CategoryRepo> { MobileCategoryRepo(get()) }
    single<ConfigRepo> { MobileConfigRepo(get()) }
}