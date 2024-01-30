package viewmodel

import org.koin.dsl.module

val viewModelModules = module {
    single { TodoViewModel() }
}