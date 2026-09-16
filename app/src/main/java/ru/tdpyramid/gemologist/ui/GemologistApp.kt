package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.components.GemItem
import ru.tdpyramid.gemologist.ui.components.GemItemModel
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GemologistApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
            )
        },
    ) { contentPadding ->
        GemologistHome(
            modifier = Modifier.padding(contentPadding),
        )
    }
}

@Composable
private fun GemologistHome(modifier: Modifier = Modifier) {
    var favoriteItemIds by remember { mutableStateOf(emptySet<String>()) }

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(
            items = SampleItems,
            key = { it.id },
        ) { item ->
            GemItem(
                item = item,
                isFavorite = item.id in favoriteItemIds,
                onFavoriteClick = {
                    favoriteItemIds = if (item.id in favoriteItemIds) {
                        favoriteItemIds - item.id
                    } else {
                        favoriteItemIds + item.id
                    }
                },
            )
        }
    }
}

private val SampleItems = listOf(
    GemItemModel("emerald", "Изумруд природный", 4.8f, Color(0xFFC8E6C9)),
    GemItemModel("sapphire", "Сапфир синий", 4.9f, Color(0xFFCAD7F2)),
    GemItemModel("ruby", "Рубин огранённый", 4.7f, Color(0xFFF2C8CE)),
    GemItemModel("amethyst", "Аметист уральский", 4.6f, Color(0xFFDDD0EF)),
    GemItemModel("topaz", "Топаз голубой", 4.8f, Color(0xFFC9E7EC)),
    GemItemModel("citrine", "Цитрин золотистый", 4.5f, Color(0xFFF4E0B8)),
)

@Preview(showBackground = true)
@Composable
private fun GemologistAppPreview() {
    GemologistTheme {
        GemologistApp()
    }
}
