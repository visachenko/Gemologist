package ru.tdpyramid.gemologist.ui.components

import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
fun PhotoItem(
    uri: Uri,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val bitmap by produceState<ImageBitmap?>(initialValue = null, uri) {
        value = withContext(Dispatchers.IO) {
            runCatching {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, uri),
                ).asImageBitmap()
            }.getOrNull()
        }
    }

    Box(modifier = modifier) {
        if (bitmap != null) {
            Image(
                bitmap = requireNotNull(bitmap),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop,
            )
        } else {
            GemPreview(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(14.dp),
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
        ) {
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = onRemove,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.remove_photo),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoItemPreview() {
    GemologistTheme {
        PhotoItem(
            uri = Uri.parse("preview://emerald"),
            onRemove = {},
            modifier = Modifier.size(136.dp),
        )
    }
}
