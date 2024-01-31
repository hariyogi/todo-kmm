package ui.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteRangePicker(
    onConfirm: (startInMillis: Long, endInMillis: Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rangePickerState = rememberDateRangePickerState()
    val readyForSave by remember {
        derivedStateOf {
            rangePickerState.selectedEndDateMillis != null
                    && rangePickerState.selectedEndDateMillis != null
        }
    }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            IconButton(onClick = { onDismiss() }) { Icon(Icons.Rounded.Close, null) }
            TextButton(
                enabled = readyForSave,
                onClick = {
                    onConfirm(
                        rangePickerState.selectedStartDateMillis!!,
                        rangePickerState.selectedEndDateMillis!!
                    )
                }
            ) { Text("Simpan") }
        }

        DateRangePicker(
            state = rangePickerState
        )
    }
}