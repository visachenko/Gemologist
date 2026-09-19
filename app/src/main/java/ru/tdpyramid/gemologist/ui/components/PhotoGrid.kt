package ru.tdpyramid.gemologist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

private const val MaxPhotoCount = 3
private const val PhotoColumnCount = 3
private val PhotoSpacing = 8.dp
private val AddPhotoColor = Color(0xFF7250B5)

@Composable
fun PhotoGrid(
    photos: List<String>,
    onRemove: (String) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visiblePhotos = photos.take(MaxPhotoCount)

    if (visiblePhotos.isEmpty()) {
        AddPhotoCell(
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp),
            onClick = onAdd,
        )
        return
    }

    val canAddPhoto = visiblePhotos.size < MaxPhotoCount
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val cellSize = (maxWidth - PhotoSpacing * (PhotoColumnCount - 1)) / PhotoColumnCount

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(cellSize),
            columns = GridCells.Fixed(PhotoColumnCount),
            horizontalArrangement = Arrangement.spacedBy(PhotoSpacing),
            verticalArrangement = Arrangement.spacedBy(PhotoSpacing),
            userScrollEnabled = false,
        ) {
            items(
                items = visiblePhotos,
                key = { it },
            ) { photo ->
                Box(modifier = Modifier.aspectRatio(1f)) {
                    GemPreview(
                        name = photo,
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(14.dp),
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        IconButton(
                            modifier = Modifier.size(32.dp),
                            onClick = { onRemove(photo) },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.remove_photo),
                            )
                        }
                    }
                }
            }

            if (canAddPhoto) {
                item(key = "add-photo") {
                    AddPhotoCell(
                        modifier = Modifier.aspectRatio(1f),
                        onClick = onAdd,
                    )
                }
            }
        }
    }
}

@Composable
private fun AddPhotoCell(
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
private fun EmptyPhotoGridPreview() {
    GemologistTheme {
        PhotoGrid(
            modifier = Modifier.padding(16.dp),
            photos = emptyList(),
            onRemove = {},
            onAdd = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PhotoGridWithAddButtonPreview() {
    GemologistTheme {
        PhotoGrid(
            modifier = Modifier.padding(16.dp),
            photos = listOf("Изумруд", "Сапфир"),
            onRemove = {},
            onAdd = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun FullPhotoGridPreview() {
    GemologistTheme {
        PhotoGrid(
            modifier = Modifier.padding(16.dp),
            photos = listOf("Изумруд", "Сапфир", "Рубин"),
            onRemove = {},
            onAdd = {},
        )
    }
}
