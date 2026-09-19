package ru.tdpyramid.gemologist.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.ui.state.MAX_GEM_PHOTO_COUNT
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

private const val PhotoColumnCount = 3
private val PhotoSpacing = 8.dp

@Composable
fun PhotoGrid(
    photos: List<Uri>,
    onRemove: (Uri) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visiblePhotos = photos.take(MAX_GEM_PHOTO_COUNT)

    if (visiblePhotos.isEmpty()) {
        AddPhotoButton(
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp),
            onClick = onAdd,
        )
        return
    }

    val canAddPhoto = visiblePhotos.size < MAX_GEM_PHOTO_COUNT
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
                PhotoItem(
                    uri = photo,
                    onRemove = { onRemove(photo) },
                    modifier = Modifier.aspectRatio(1f),
                )
            }

            if (canAddPhoto) {
                item(key = "add-photo") {
                    AddPhotoButton(
                        modifier = Modifier.aspectRatio(1f),
                        onClick = onAdd,
                    )
                }
            }
        }
    }
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
            photos = listOf(Uri.parse("preview://emerald"), Uri.parse("preview://sapphire")),
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
            photos = listOf(
                Uri.parse("preview://emerald"),
                Uri.parse("preview://sapphire"),
                Uri.parse("preview://ruby"),
            ),
            onRemove = {},
            onAdd = {},
        )
    }
}
