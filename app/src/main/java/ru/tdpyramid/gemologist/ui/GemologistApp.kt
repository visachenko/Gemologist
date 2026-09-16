package ru.tdpyramid.gemologist.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
    var items by remember { mutableStateOf(SampleGemItems) }
    var selectedItemId by remember { mutableStateOf<String?>(null) }
    var isAddingItem by remember { mutableStateOf(false) }
    var favoriteItemIds by remember { mutableStateOf(emptySet<String>()) }
    var nextCustomItemId by remember { mutableStateOf(1) }
    val selectedItem = items.firstOrNull { it.id == selectedItemId }

    BackHandler(enabled = isAddingItem || selectedItem != null) {
        if (isAddingItem) isAddingItem = false else selectedItemId = null
    }

    if (isAddingItem) {
        AddGemScreen(
            modifier = modifier,
            onBackClick = { isAddingItem = false },
            onAddClick = { name, rating, tags ->
                items = items + GemItemModel(
                    id = "custom-${nextCustomItemId++}",
                    name = name,
                    rating = rating,
                    tags = tags,
                )
                isAddingItem = false
            },
        )
    } else if (selectedItem == null) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { isAddingItem = true },
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_gem),
                    )
                }
            },
        ) { contentPadding ->
            GemologistHome(
                modifier = Modifier.padding(contentPadding),
                items = items,
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
    items: List<GemItemModel>,
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
            items = items,
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
