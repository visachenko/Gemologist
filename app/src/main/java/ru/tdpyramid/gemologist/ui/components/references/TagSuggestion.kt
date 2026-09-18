package ru.tdpyramid.gemologist.ui.components.references

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val Purple = Color(0xFF7250B5)

@Composable
fun TagSuggestion(
    tag: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(enabled = !selected, onClick = onClick),
        shape = CircleShape,
        color = if (selected) Purple.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            text = tag,
            color = if (selected) Purple else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}