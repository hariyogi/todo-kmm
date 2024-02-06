import handler.ConfigHandler
import org.koin.dsl.module

val handlerModules = module {
    single { ConfigHandler(get()) }
}