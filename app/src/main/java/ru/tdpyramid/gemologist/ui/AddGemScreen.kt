package ru.tdpyramid.gemologist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tdpyramid.gemologist.R
import ru.tdpyramid.gemologist.ui.components.GemPreview
import ru.tdpyramid.gemologist.ui.components.PreviewIconButton
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddGemScreen(
    onBackClick: () -> Unit,
    onAddClick: (name: String, rating: Float, tags: List<String>, comment: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(0) }
    var tagText by remember { mutableStateOf("") }
    var selectedTags by remember { mutableStateOf(emptyList<String>()) }
    var comment by remember { mutableStateOf("") }
    var photoPlaceholders by remember { mutableStateOf(emptyList<String>()) }
    var nextPhotoId by remember { mutableIntStateOf(1) }
    val tagsBringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    fun addTag(tagValue: String = tagText) {
        val tag = tagValue.trim().lowercase()
        if (tag.isNotEmpty() && tag !in selectedTags) selectedTags = selectedTags + tag
        tagText = ""
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Surface(
                modifier = Modifier.navigationBarsPadding(),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                        .height(56.dp),
                    enabled = name.isNotBlank() && rating > 0,
                    onClick = {
                        onAddClick(name.trim(), rating.toFloat(), selectedTags, comment.trim())
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Purple),
                ) {
                    Text(
                        text = stringResource(R.string.add),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(72.dp),
                ) {
                    PreviewIconButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp),
                        onClick = onBackClick,
                        contentDescription = stringResource(R.string.back),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            }

            item {
                PhotoGrid(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    photos = photoPlaceholders,
                    onRemove = { photoPlaceholders = photoPlaceholders - it },
                    onAdd = {
                        if (photoPlaceholders.size < MaxPhotoCount) {
                            photoPlaceholders = photoPlaceholders + "custom-photo-${nextPhotoId++}"
                        }
                    },
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(22.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        label = { Text(stringResource(R.string.name)) },
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stringResource(R.string.rating),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 4.dp,
                                alignment = Alignment.CenterHorizontally,
                            ),
                        ) {
                            (1..5).forEach { value ->
                                IconButton(onClick = { rating = value }) {
                                    Icon(
                                        modifier = Modifier.size(46.dp),
                                        imageVector = if (value <= rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                        contentDescription = stringResource(R.string.rating_value, value),
                                        tint = if (value <= rating) RatingStarColor else MaterialTheme.colorScheme.outline,
                                    )
                                }
                            }
                        }
                    }

                    LabeledField(
                        label = stringResource(R.string.tags),
                        labelModifier = Modifier.bringIntoViewRequester(tagsBringIntoViewRequester),
                    ) {
                        if (selectedTags.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                selectedTags.forEach { tag ->
                                    InputChip(
                                        selected = true,
                                        onClick = { selectedTags = selectedTags - tag },
                                        shape = CircleShape,
                                        label = { Text(tag) },
                                        trailingIcon = {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                imageVector = Icons.Filled.Close,
                                                contentDescription = stringResource(R.string.remove_tag, tag),
                                            )
                                        },
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            tagsBringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            value = tagText,
                            onValueChange = { tagText = it },
                            singleLine = true,
                            placeholder = { Text(stringResource(R.string.tags_hint)) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { addTag() }),
                            trailingIcon = {
                                if (tagText.isNotEmpty()) {
                                    IconButton(onClick = { tagText = "" }) {
                                        Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = stringResource(R.string.clear),
                                        )
                                    }
                                }
                            },
                        )

                        val query = tagText.trim()
                        val suggestions = if (query.isEmpty()) {
                            emptyList()
                        } else {
                            SuggestedTags.filter {
                                it !in selectedTags && it.contains(query, ignoreCase = true)
                            }
                        }

                        if (suggestions.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                suggestions.forEach { tag ->
                                    TagSuggestion(
                                        tag = tag,
                                        selected = false,
                                        onClick = { addTag(tag) },
                                    )
                                }
                            }
                        } else if (query.isNotEmpty() && query !in selectedTags) {
                            OutlinedButton(onClick = { addTag(query) }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = null,
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(R.string.add_named_tag, query),
                                )
                            }
                        }
                    }

                    LabeledField(label = stringResource(R.string.comment)) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = comment,
                            onValueChange = { comment = it },
                            minLines = 4,
                            maxLines = 7,
                            placeholder = { Text(stringResource(R.string.comment_hint)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoGrid(
    photos: List<String>,
    onRemove: (String) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (photos.isEmpty()) {
        AddPhotoCell(
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp),
            onClick = onAdd,
        )
    } else {
        val canAddPhoto = photos.size < MaxPhotoCount
        val itemCount = photos.size + if (canAddPhoto) 1 else 0
        val rowCount = (itemCount + PhotoColumnCount - 1) / PhotoColumnCount
        val gridHeight = PhotoCellHeight * rowCount + PhotoGridSpacing * (rowCount - 1)

        LazyVerticalGrid(
            modifier = modifier
                .fillMaxWidth()
                .height(gridHeight),
            columns = GridCells.Fixed(PhotoColumnCount),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(
                items = photos,
                key = { it },
            ) { photo ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(PhotoCellHeight),
                ) {
                    GemPreview(
                        name = photo,
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(14.dp),
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        IconButton(
                            modifier = Modifier.size(32.dp),
                            onClick = { onRemove(photo) },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.remove_photo),
                            )
                        }
                    }
                }
            }

            if (canAddPhoto) {
                item(key = "add-photo") {
                    AddPhotoCell(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(PhotoCellHeight),
                        onClick = onAdd,
                    )
                }
            }
        }
    }
}

@Composable
private fun AddPhotoCell(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .dashedBorder(MaterialTheme.colorScheme.outline.copy(alpha = 0.55f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = CircleShape,
                color = Purple.copy(alpha = 0.12f),
            ) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Purple,
                )
            }
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = stringResource(R.string.add_photo),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun Modifier.dashedBorder(color: Color): Modifier = drawBehind {
    drawRoundRect(
        color = color,
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(14.dp.toPx()),
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(9f, 7f)),
        ),
    )
}

@Composable
private fun LabeledField(
    label: String,
    labelModifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            modifier = labelModifier,
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
        content()
    }
}

@Composable
private fun TagSuggestion(
    tag: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(enabled = !selected, onClick = onClick),
        shape = CircleShape,
        color = if (selected) Purple.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            text = tag,
            color = if (selected) Purple else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private val SuggestedTags = listOf("зелёный", "прозрачный", "драгоценный", "редкий")
private val Purple = Color(0xFF7250B5)
private val RatingStarColor = Color(0xFFFFB300)
private const val PhotoColumnCount = 3
private const val MaxPhotoCount = 6
private val PhotoCellHeight = 112.dp
private val PhotoGridSpacing = 8.dp

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun AddGemScreenPreview() {
    GemologistTheme {
        AddGemScreen(
            onBackClick = {},
            onAddClick = { _, _, _, _ -> },
        )
    }
}
