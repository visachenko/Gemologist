package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.ui.components.GemPreview
import ru.tdpyramid.gemologist.ui.components.PreviewIconButton
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme
import java.util.Locale

@Composable
fun GemDetailsScreen(
    gem: Gem?,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            GemPreview(
                name = gem?.name ?: "",
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(0.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PreviewIconButton(
                    onClick = onBackClick,
                    contentDescription = stringResource(R.string.back),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }

                PreviewIconButton(
                    onClick = onFavoriteClick,
                    contentDescription = stringResource(
                        if (gem?.isFavorite ?: false) R.string.remove_from_favorites else R.string.add_to_favorites,
                    ),
                ) {
                    Icon(
                        imageVector = if (gem?.isFavorite ?: false) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (gem?.isFavorite ?: false) FavoriteColor else MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = gem?.name ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = RatingStarColor,
                )
                Text(
                    text = gem?.let {
                        String.format(Locale.getDefault(), "%.1f", it.rating)
                    }.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                emptyList<String>().forEach { tag ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape,
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            text = tag,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            if (!gem?.comment.isNullOrBlank()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.comment),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = gem.comment,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

private val FavoriteColor = Color(0xFFE53935)
private val RatingStarColor = Color(0xFFFFB300)

@Preview(showBackground = true)
@Composable
private fun GemDetailsScreenPreview() {
    GemologistTheme {
        GemDetailsScreen(
            gem = Gem(
                id = 0,
                name = "Изумруд природный",
                rating = 4f,
                comment = "Насыщенный зелёный цвет, без заметных включений.",
                isFavorite = true
            ),
            onBackClick = {},
            onFavoriteClick = {},
        )
    }
}
