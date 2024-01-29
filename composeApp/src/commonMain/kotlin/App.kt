import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.repo.TodoRepo
import org.koin.compose.koinInject
import ui.TodoInput
import ui.TodoList

@Composable
fun App() {
    val sqlDelightTodoRepo = koinInject<TodoRepo>()
    var todoList by remember { mutableStateOf(sqlDelightTodoRepo.findAll()) }

    MaterialTheme {
        Column {
            TodoInput(onClick = {
                sqlDelightTodoRepo.create(
                    it.summary,
                    it.description,
                    false
                )
                val list = sqlDelightTodoRepo.findAll()
                todoList = list
            })
            Spacer(Modifier.height(16.dp))
            Text("Todo List")
            Spacer(Modifier.height(8.dp))
            TodoList(todoList)
        }
    }
}