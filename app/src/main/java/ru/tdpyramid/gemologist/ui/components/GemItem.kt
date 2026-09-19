package ru.tdpyramid.gemologist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme
import java.util.Locale

@Composable
fun GemItem(
    item: Gem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isFavorite = item.isFavorite
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            GemImage(
                uri = item.photoUris.firstOrNull(),
                placeholderColor = gemPreviewColor(item.name),
                modifier = Modifier.fillMaxSize(),
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp),
                onClick = onFavoriteClick,
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(
                        if (isFavorite) R.string.remove_from_favorites else R.string.add_to_favorites,
                    ),
                    tint = if (isFavorite) FavoriteColor else FavoriteOutlineColor,
                )
            }
        }

        Text(
            text = item.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = RatingStarColor,
            )
            Text(
                text = String.format(Locale.getDefault(), "%.1f", item.rating),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

private val FavoriteColor = Color(0xFFE53935)
private val FavoriteOutlineColor = Color(0xFF8E8E93)
private val RatingStarColor = Color(0xFFFFB300)

@Preview(showBackground = true, widthDp = 180)
@Composable
private fun GemItemPreview() {
    GemologistTheme {
        GemItem(
            modifier = Modifier.padding(16.dp),
            item = Gem(
                id = 0,
                name = "Изумруд",
                rating = 4f,
                comment = "",
                isFavorite = true
            ),
            onClick = {},
            onFavoriteClick = {},
        )
    }
}
