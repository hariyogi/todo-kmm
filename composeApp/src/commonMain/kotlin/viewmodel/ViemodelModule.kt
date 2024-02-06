package viewmodel

import org.koin.dsl.module

val viewModelModules = module {
    single { TodoViewModel() }
    single { CategoryBsViewModel(get()) }
    single { TodoInputViewModel() }
    single { AppConfigViewModel(get()) }
}