package ru.tdpyramid.gemologist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
fun GemPreview(
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(color.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Outlined.Diamond,
            contentDescription = null,
            tint = color,
        )
    }
}

fun gemPreviewColor(seed: String): Color = Color.hsl(
    hue = ((seed.hashCode() and Int.MAX_VALUE) % 360).toFloat(),
    saturation = 0.42f,
    lightness = 0.38f,
)

@Preview(showBackground = true)
@Composable
private fun GemPreviewPreview() {
    GemologistTheme {
        GemPreview(
            color = Color(0xFF357A38),
            modifier = Modifier.size(136.dp),
        )
    }
}
