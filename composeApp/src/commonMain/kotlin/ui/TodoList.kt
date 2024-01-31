package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import database.dto.TodoDto
import utils.viewMode
import java.time.LocalDateTime


@Composable
fun TodoList(
    todoItems: List<TodoDto>,
    onDelete: (item: TodoDto) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(todoItems) { item ->
            TodoListItem(
                todoDto = item,
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun TodoListItem(
    todoDto: TodoDto,
    onDelete: (item: TodoDto) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todoDto.summary,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = todoDto.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                TodoItemDateRange(todoDto.startAt, todoDto.endAt)
            }

            IconButton(onClick = {
                onDelete(todoDto)
            }) {
                Icon(Icons.Rounded.Delete, null)
            }
        }
    }
}

@Composable
fun TodoItemDateRange(start: LocalDateTime?, end: LocalDateTime?, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        if (start != null || end != null) {
            if (start != null) {
                Text(text = "Start ${start.viewMode()}")
            }

            if (start != null && end != null) {
                Text(text = " - ")
            }

            if (end != null) {
                Text(text = "End ${end.viewMode()}")
            }
        }
    }
}