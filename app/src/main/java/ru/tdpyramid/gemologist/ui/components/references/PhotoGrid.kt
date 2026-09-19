package ru.tdpyramid.gemologist.ui.components.references

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.components.GemPreview
import ru.tdpyramid.gemologist.ui.components.gemPreviewColor

@Composable
fun PhotoGrid(
    photos: List<String>,
    maxPhotoCount: Int,
    columnCount: Int,
    cellHeight: Dp,
    spacing: Dp,
    onRemove: (String) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (photos.isEmpty()) {
        AddPhotoCell(
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp),
            onClick = onAdd,
        )
    } else {
        val canAddPhoto = photos.size < maxPhotoCount
        val itemCount = photos.size + if (canAddPhoto) 1 else 0
        val rowCount = (itemCount + columnCount - 1) / columnCount
        val gridHeight = cellHeight * rowCount + spacing * (rowCount - 1)

        LazyVerticalGrid(
            modifier = modifier
                .fillMaxWidth()
                .height(gridHeight),
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(
                items = photos,
                key = { it },
            ) { photo ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cellHeight),
                ) {
                    GemPreview(
                        color = gemPreviewColor(photo),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cellHeight),
                        onClick = onAdd,
                    )
                }
            }
        }
    }
}
