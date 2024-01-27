import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.JvmDatabaseImpl
import id.harlabs.delight.gen.Database
import org.koin.compose.KoinApplication
import org.koin.dsl.module

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Todo list") {
        val databaseModule = module {
            single<Database> { JvmDatabaseImpl().createDatabase() }
        }
        KoinApplication({
            modules(databaseModule)
        }) {
            App()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}