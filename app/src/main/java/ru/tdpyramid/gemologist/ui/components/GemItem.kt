package ru.tdpyramid.gemologist.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Diamond
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

data class GemItemModel(
    val id: String,
    val name: String,
    val rating: Float,
    val tags: List<String>,
    val comment: String = "",
)

@Composable
fun GemItem(
    item: GemItemModel,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            GemPreview(
                name = item.name,
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
            minLines = 2,
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
                text = item.rating.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun GemPreview(
    name: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(placeholderColor(name)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Outlined.Diamond,
            contentDescription = null,
            tint = placeholderIconColor(name),
        )
    }
}

private val FavoriteColor = Color(0xFFE53935)
private val FavoriteOutlineColor = Color(0xFF8E8E93)
private val RatingStarColor = Color(0xFFFFB300)

private fun placeholderColor(text: String): Color {
    return Color.hsl(
        hue = placeholderHue(text),
        saturation = 0.35f,
        lightness = 0.82f,
    )
}

private fun placeholderIconColor(text: String): Color {
    return Color.hsl(
        hue = placeholderHue(text),
        saturation = 0.42f,
        lightness = 0.38f,
    )
}

private fun placeholderHue(text: String): Float {
    return ((text.hashCode() and Int.MAX_VALUE) % 360).toFloat()
}

@Preview(showBackground = true, widthDp = 180)
@Composable
private fun GemItemPreview() {
    GemologistTheme {
        GemItem(
            modifier = Modifier.padding(16.dp),
            item = GemItemModel(
                id = "emerald",
                name = "Изумруд природный",
                rating = 4.8f,
                tags = listOf("Природный", "Зелёный"),
            ),
            isFavorite = false,
            onClick = {},
            onFavoriteClick = {},
        )
    }
}
