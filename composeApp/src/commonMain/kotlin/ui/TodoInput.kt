package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TodoInputResult(val summary: String, val description: String)

@Composable
fun TodoInput(
    onClick: (result: TodoInputResult) -> Unit,
    modifier: Modifier = Modifier
) {

    var summaryValue by remember { mutableStateOf("") }
    var descValue by remember { mutableStateOf("") }
    var saveNote by remember { mutableStateOf("") }


    Column(modifier = modifier) {
        TextField(
            value = summaryValue,
            onValueChange = { summaryValue = it },
            label = { Text("Summary") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = descValue,
            onValueChange = { descValue = it},
            label = { Text("Description") }
        )
        Spacer(Modifier.height(16.dp))
        if (saveNote.isNotBlank()) {
            Text("Saved todo $saveNote")
            Spacer(Modifier.height(16.dp))
        }
        ElevatedButton(onClick = {
            saveNote = summaryValue
            val result = TodoInputResult(summaryValue, descValue)
            onClick(result)
        }) {
            Text("Sipam todo list")
        }
    }
}