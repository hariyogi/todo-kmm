package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import database.dto.CategoryDto
import database.dto.TodoDto
import database.repo.CategoryRepo
import isCompact
import org.koin.compose.koinInject
import ui.share.CategoryChip
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun TodoListItem(
    todoDto: TodoDto,
    onDelete: (item: TodoDto) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryRepo = koinInject<CategoryRepo>()
    var categoryDto by remember { mutableStateOf<CategoryDto?>(null) }

    val windowSize = calculateWindowSizeClass()

    if (todoDto.catId != null) {
        LaunchedEffect(Unit) {
            categoryDto = categoryRepo.findById(todoDto.catId!!).await()
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(16.dp))
        if (windowSize.isCompact()) {
            TodoSummaryCompact(
                summary = todoDto.summary,
                categoryDto = categoryDto,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        } else {
            TodoSummaryExpanded(
                summary = todoDto.summary,
                categoryDto = categoryDto,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = todoDto.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))
                TodoItemDateRange(todoDto.startAt, todoDto.endAt)
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onDelete(todoDto)
                },
            ) {
                Icon(Icons.Rounded.Delete, null)
                Text("Hapus")
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun TodoSummaryExpanded(
    summary: String,
    categoryDto: CategoryDto?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        if (categoryDto != null) {
            CategoryChip(onClick = {}, category = categoryDto)
        }
    }
}

@Composable
private fun TodoSummaryCompact(
    summary: String,
    categoryDto: CategoryDto?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        if (categoryDto != null) {
            CategoryChip(onClick = {}, category = categoryDto)
        }
    }
}

@Composable
private fun TodoItemDateRange(
    start: LocalDateTime?,
    end: LocalDateTime?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if (start != null) {
            Row {
                Icon(Icons.Rounded.Notifications, null)
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "start : ${start.viewMode()}",
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        if (end != null) {
            Row {
                Icon(Icons.Rounded.Notifications, null)
                Spacer(Modifier.width(4.dp))
                Text("end : ${end.viewMode()}")
            }
        }
    }
}