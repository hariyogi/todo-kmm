package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import database.dto.TodoDto


@Composable
fun TodoList(todoItems: List<TodoDto>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(todoItems) { item ->
            TodoListItem(item)
        }
    }
}

@Composable
fun TodoListItem(todoDto: TodoDto, modifier: Modifier = Modifier) {
    Column {
        Text("Summary : ${todoDto.summary}")
        Text("Description ${todoDto.description}")
    }
}