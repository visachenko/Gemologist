package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
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
        GemologistHome(contentPadding)
    }
}

@Composable
private fun GemologistHome(contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "◇",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge,
        )
        Text(
            text = stringResource(R.string.compose_ready_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.compose_ready_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GemologistAppPreview() {
    GemologistTheme {
        GemologistApp()
    }
}
