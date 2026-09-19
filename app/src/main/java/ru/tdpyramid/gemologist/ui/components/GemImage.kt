package ru.tdpyramid.gemologist.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
fun GemImage(
    uri: Uri?,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
) {
    if (uri == null) {
        GemPreview(
            color = placeholderColor,
            modifier = modifier,
            shape = shape,
        )
        return
    }

    SubcomposeAsyncImage(
        model = uri,
        contentDescription = null,
        modifier = modifier.clip(shape),
        contentScale = ContentScale.Crop,
        loading = {
            GemPreview(
                color = placeholderColor,
                modifier = Modifier.fillMaxSize(),
                shape = shape,
            )
        },
        error = {
            GemPreview(
                color = placeholderColor,
                modifier = Modifier.fillMaxSize(),
                shape = shape,
            )
        },
        success = { SubcomposeAsyncImageContent() },
    )
}

@Preview(showBackground = true)
@Composable
private fun GemImagePreview() {
    GemologistTheme {
        GemImage(
            uri = null,
            placeholderColor = Color(0xFF357A38),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
