package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.share.NoteRangePicker
import utils.toDateTimeViewMode
import utils.toLocalDateTime
import java.time.LocalDateTime

data class TodoInputResult(
    val summary: String,
    val description: String,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?
)

data class NoteRange(val start: Long, val end: Long)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInput(
    onClick: (result: TodoInputResult) -> Unit,
    modifier: Modifier = Modifier
) {

    var summaryValue by remember { mutableStateOf("") }
    var descValue by remember { mutableStateOf("") }
    var saveNote by remember { mutableStateOf("") }
    var noteRange by remember { mutableStateOf<NoteRange?>(null) }
    var openDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (openDialog) {
            DatePickerDialog(
                onDismissRequest = { openDialog = false },
                confirmButton = { }
            ) {
                NoteRangePicker(
                    onConfirm = { start, end ->
                        noteRange = NoteRange(start, end)
                        openDialog = false
                    },
                    onDismiss = { openDialog = false }
                )
            }

        }

        OutlinedTextField(
            value = summaryValue,
            onValueChange = { summaryValue = it },
            label = { Text("Summary") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = descValue,
            onValueChange = { descValue = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Mulai dari",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = noteRange?.start.toDateTimeViewMode() ?: "Tidak ada",
                onValueChange = {},
                label = { Text("Mulai dari") },
                enabled = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = noteRange?.end.toDateTimeViewMode() ?: "Tidak ada",
                onValueChange = {},
                label = { Text("Sampai") },
                enabled = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    openDialog = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mulai dari")
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            saveNote = summaryValue
            val result = TodoInputResult(
                summaryValue,
                descValue,
                noteRange?.start?.toLocalDateTime(),
                noteRange?.end?.toLocalDateTime()
            )
            onClick(result)
        }) {
            Text("Simpan todo list")
        }
    }
}
