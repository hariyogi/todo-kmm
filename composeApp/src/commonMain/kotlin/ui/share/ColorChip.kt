package ui.share

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import database.dto.CategoryDto
import toColor

@Composable
fun CategoryChip(
    onClick: (item: CategoryDto) -> Unit,
    category: CategoryDto
) {
    AssistChip(
        onClick = { onClick(category) },
        label = { Text(category.name) },
        leadingIcon = {
            Circle(15.dp, 15.dp, category.color?.toColor() ?: Color.Transparent)
        }
    )
}