package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.state.AddGemState
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGemScreen(
    state: AddGemState,
    onNameChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    onCommentChange: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (state.isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                            contentDescription = stringResource(
                                if (state.isFavorite) {
                                    R.string.remove_from_favorites
                                } else {
                                    R.string.add_to_favorites
                                },
                            ),
                            tint = if (state.isFavorite) FavoriteColor else MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 16.dp,
            ) {
                Button(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    enabled = state.name.isNotBlank(),
                    onClick = onAddClick,
                    shape = CircleShape,
                ) {
                    Text(
                        text = stringResource(R.string.add),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = onNameChange,
                singleLine = true,
                label = { Text(stringResource(R.string.name)) },
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.rating),
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    (1..5).forEach { rating ->
                        IconButton(onClick = { onRatingChange(rating.toFloat()) }) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = if (rating <= state.rating) {
                                    Icons.Filled.Star
                                } else {
                                    Icons.Outlined.StarBorder
                                },
                                contentDescription = stringResource(R.string.rating_value, rating),
                                tint = if (rating <= state.rating) {
                                    RatingStarColor
                                } else {
                                    MaterialTheme.colorScheme.outline
                                },
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.comment,
                onValueChange = onCommentChange,
                minLines = 4,
                maxLines = 7,
                label = { Text(stringResource(R.string.comment)) },
                placeholder = { Text(stringResource(R.string.comment_hint)) },
            )
        }
    }
}

private val FavoriteColor = Color(0xFFE53935)
private val RatingStarColor = Color(0xFFFFB300)

@Preview(showBackground = true, heightDp = 760)
@Composable
private fun AddGemScreenPreview() {
    GemologistTheme {
        AddGemScreen(
            state = AddGemState(name = "Изумруд", rating = 4f),
            onNameChange = {},
            onRatingChange = {},
            onCommentChange = {},
            onFavoriteClick = {},
            onBackClick = {},
            onAddClick = {},
        )
    }
}
