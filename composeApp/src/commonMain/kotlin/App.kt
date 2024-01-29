import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.dto.TodoDto
import database.repo.TodoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.TodoInput
import ui.TodoList

@Composable
fun App() {
    val sqlDelightTodoRepo = koinInject<TodoRepo>()
    val composeScope = rememberCoroutineScope()
    var todoList by remember { mutableStateOf(listOf<TodoDto>()) }

    if (todoList.isEmpty()) {
        LaunchedEffect(todoList) {
            composeScope.launch(Dispatchers.IO) {
                val result = sqlDelightTodoRepo.findAll()
                todoList = result.await()
            }
        }
    }

    MaterialTheme {
        Column {
            TodoInput(onClick = {
                composeScope.launch (Dispatchers.IO) {
                    sqlDelightTodoRepo.create(
                        it.summary,
                        it.description,
                        false
                    ).await()
                    val list = sqlDelightTodoRepo.findAll()
                    todoList = list.await()
                }
            })
            Spacer(Modifier.height(16.dp))
            Text("Todo List")
            Spacer(Modifier.height(8.dp))
            TodoList(
                todoList,
                Modifier
                    .fillMaxHeight()
            )
        }
    }
}