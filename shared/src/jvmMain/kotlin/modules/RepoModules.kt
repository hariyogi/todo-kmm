package modules

import database.repo.CategoryRepo
import database.repo.CategoryRepoImpl
import database.repo.ConfigRepo
import database.repo.ConfigRepoImpl
import database.repo.TodoRepo
import database.repo.TodoRepoImpl
import org.koin.dsl.module

val repoModules = module {
    single<TodoRepo> { TodoRepoImpl(get()) }
    single<CategoryRepo> { CategoryRepoImpl(get()) }
    single<ConfigRepo> { ConfigRepoImpl(get()) }
}