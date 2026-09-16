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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.components.GemItem
import ru.tdpyramid.gemologist.domain.Gem
import ru.tdpyramid.gemologist.ui.data.SampleGemItems
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GemologistApp(
    viewModel: GemologistViewModel,
    modifier: Modifier = Modifier,
) {
    val items by viewModel.gems.collectAsStateWithLifecycle()

    GemologistContent(
        items = items,
        onAddItem = viewModel::addGem,
        onToggleFavorite = { viewModel.toggleFavorite(it.id) },
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GemologistContent(
    items: List<Gem>,
    onAddItem: (String, Float, List<String>, String) -> Unit,
    onToggleFavorite: (Gem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItemId by remember { mutableStateOf<Long?>(null) }
    var isAddingItem by remember { mutableStateOf(false) }
    val selectedItem = items.firstOrNull { it.id == selectedItemId }

    BackHandler(enabled = isAddingItem || selectedItem != null) {
        if (isAddingItem) isAddingItem = false else selectedItemId = null
    }

    if (isAddingItem) {
        AddGemScreen(
            modifier = modifier,
            onBackClick = { isAddingItem = false },
            onAddClick = { name, rating, tags, comment ->
                onAddItem(name, rating, tags, comment)
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
                onItemClick = { selectedItemId = it.id },
                onFavoriteClick = onToggleFavorite,
            )
        }
    } else {
        GemDetailsScreen(
            modifier = modifier,
            item = selectedItem,
            onBackClick = { selectedItemId = null },
            onFavoriteClick = { onToggleFavorite(selectedItem) },
        )
    }
}

@Composable
private fun GemologistHome(
    items: List<Gem>,
    onItemClick: (Gem) -> Unit,
    onFavoriteClick: (Gem) -> Unit,
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
                onClick = { onItemClick(item) },
                onFavoriteClick = { onFavoriteClick(item) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GemologistAppPreview() {
    GemologistTheme {
        GemologistContent(
            items = SampleGemItems,
            onAddItem = { _, _, _, _ -> },
            onToggleFavorite = {},
        )
    }
}
