package id.harlabs.example.todo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import database.AndroidDatabaseImpl
import database.modules.repoModules
import handlerModules
import id.harlabs.delight.gen.Database
import org.koin.core.context.startKoin
import org.koin.dsl.module
import viewmodel.viewModelModules

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseModule = module {
            single<Database> {
                AndroidDatabaseImpl(applicationContext)
                    .createDatabase()
            }
        }

        startKoin {
            modules(
                databaseModule,
                repoModules,
                viewModelModules,
                handlerModules
            )
        }

        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}