package ru.tdpyramid.gemologist.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.components.GemItem
import ru.tdpyramid.gemologist.ui.components.GemItemModel
import ru.tdpyramid.gemologist.ui.data.SampleGemItems
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GemologistApp(modifier: Modifier = Modifier) {
    var selectedItemId by remember { mutableStateOf<String?>(null) }
    var favoriteItemIds by remember { mutableStateOf(emptySet<String>()) }
    val selectedItem = SampleGemItems.firstOrNull { it.id == selectedItemId }

    BackHandler(enabled = selectedItem != null) {
        selectedItemId = null
    }

    if (selectedItem == null) {
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
                favoriteItemIds = favoriteItemIds,
                onItemClick = { selectedItemId = it.id },
                onFavoriteClick = { item ->
                    favoriteItemIds = favoriteItemIds.toggle(item.id)
                },
            )
        }
    } else {
        GemDetailsScreen(
            modifier = modifier,
            item = selectedItem,
            isFavorite = selectedItem.id in favoriteItemIds,
            onBackClick = { selectedItemId = null },
            onFavoriteClick = {
                favoriteItemIds = favoriteItemIds.toggle(selectedItem.id)
            },
        )
    }
}

@Composable
private fun GemologistHome(
    favoriteItemIds: Set<String>,
    onItemClick: (GemItemModel) -> Unit,
    onFavoriteClick: (GemItemModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = SampleGemItems,
            key = { it.id },
        ) { item ->
            GemItem(
                item = item,
                isFavorite = item.id in favoriteItemIds,
                onClick = { onItemClick(item) },
                onFavoriteClick = { onFavoriteClick(item) },
            )
        }
    }
}

private fun Set<String>.toggle(id: String): Set<String> {
    return if (id in this) this - id else this + id
}

@Preview(showBackground = true)
@Composable
private fun GemologistAppPreview() {
    GemologistTheme {
        GemologistApp()
    }
}
