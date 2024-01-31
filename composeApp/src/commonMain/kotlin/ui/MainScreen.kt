@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")
package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.repo.TodoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val sqlDelightTodoRepo = koinInject<TodoRepo>()
    val todoViewModel = koinViewModel<TodoViewModel>()

    val uiState by todoViewModel.uiState.collectAsState()

    val composeScope = rememberCoroutineScope()

    if (uiState.todoList.isEmpty()) {
        LaunchedEffect(Unit) {
            composeScope.launch(Dispatchers.IO) {
                val result = sqlDelightTodoRepo.findAll()
                todoViewModel.addTodo(result.await())
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Todo list") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navigator.navigate("/input")
                    }) {
                        Icon(Icons.Rounded.Add, null)
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {
            TodoList(
                todoItems = uiState.todoList,
                onDelete = {
                    composeScope.launch(Dispatchers.IO) {
                        sqlDelightTodoRepo.delete(it.id).await()
                        todoViewModel.deleteTodo(it)
                    }
                },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

}