package ru.tdpyramid.gemologist.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
fun GemPhotoCarousel(
    gemName: String,
    photos: List<Uri>,
    modifier: Modifier = Modifier,
) {
    val placeholderColor = gemPreviewColor(gemName)

    if (photos.size <= 1) {
        GemImage(
            uri = photos.firstOrNull(),
            placeholderColor = placeholderColor,
            modifier = modifier,
            shape = RoundedCornerShape(0.dp),
        )
        return
    }

    val pagerState = rememberPagerState(pageCount = photos::size)
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            GemImage(
                uri = photos[page],
                placeholderColor = placeholderColor,
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(0.dp),
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.28f),
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(horizontal = 10.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            photos.indices.forEach { page ->
                val selected = page == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .size(if (selected) 9.dp else 7.dp)
                        .background(
                            color = if (selected) Color.White else Color.White.copy(alpha = 0.55f),
                            shape = CircleShape,
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 360)
@Composable
private fun GemPhotoCarouselPreview() {
    GemologistTheme {
        GemPhotoCarousel(
            gemName = "Изумруд",
            photos = listOf(
                Uri.parse("preview://emerald-1"),
                Uri.parse("preview://emerald-2"),
                Uri.parse("preview://emerald-3"),
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
