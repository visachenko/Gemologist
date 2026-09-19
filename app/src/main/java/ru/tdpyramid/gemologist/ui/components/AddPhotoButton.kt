package ru.tdpyramid.gemologist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

private val AddPhotoColor = Color(0xFF7250B5)

@Composable
fun AddPhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .dashedBorder(MaterialTheme.colorScheme.outline.copy(alpha = 0.55f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = CircleShape,
                color = AddPhotoColor.copy(alpha = 0.12f),
            ) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = AddPhotoColor,
                )
            }
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = stringResource(R.string.add_photo),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun Modifier.dashedBorder(color: Color): Modifier = drawBehind {
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(14.dp.toPx()),
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(9f, 7f)),
        ),
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun AddPhotoButtonPreview() {
    GemologistTheme {
        AddPhotoButton(
            onClick = {},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(112.dp),
        )
    }
}
