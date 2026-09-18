package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.ui.components.GemItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GemListScreen(
    gems: List<Gem>,
    onItemClick: (Gem) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_gem),
                )
            }
        },
    ) { contentPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(contentPadding),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = gems,
                key = { it.id },
            ) { gem ->
                GemItem(
                    item = gem,
                    onClick = { onItemClick(gem) },
                    onFavoriteClick = { onFavoriteClick(gem.id) },
                )
            }
        }
    }
}
