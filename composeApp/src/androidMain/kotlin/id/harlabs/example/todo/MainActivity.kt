package id.harlabs.example.todo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import database.AndroidDatabaseImpl
import id.harlabs.delight.gen.Database
import org.koin.compose.KoinApplication
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseModule = module {
            single<Database> {
                AndroidDatabaseImpl(applicationContext)
                    .createDatabase()
            }
        }

        setContent {
            KoinApplication({
                modules(databaseModule)
            }) {
                App()
            }
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}