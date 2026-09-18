//package ru.tdpyramid.gemologist.ui.references
//
//import android.R.attr.name
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.relocation.bringIntoViewRequester
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material.icons.outlined.StarBorder
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.InputChip
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//import ru.tdpyramid.gemologist.R
//import ru.tdpyramid.gemologist.ui.components.references.LabeledField
//import ru.tdpyramid.gemologist.ui.components.references.PhotoGrid
//import ru.tdpyramid.gemologist.ui.components.PreviewIconButton
//import ru.tdpyramid.gemologist.ui.components.references.TagSuggestion
//import ru.tdpyramid.gemologist.ui.state.AddGemState
//import ru.tdpyramid.gemologist.ui.theme.GemologistTheme
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun AddGemScreen(
//    state: AddGemState,
//    onBackClick: () -> Unit,
//    onAddClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Scaffold(
//        modifier = modifier.fillMaxSize(),
//        containerColor = MaterialTheme.colorScheme.surface,
//        contentWindowInsets = WindowInsets(0, 0, 0, 0),
//        bottomBar = {
//            Surface(
//                modifier = Modifier.navigationBarsPadding(),
//                color = MaterialTheme.colorScheme.surface,
//            ) {
//                Button(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp, vertical = 14.dp)
//                        .height(56.dp),
//                    enabled = state.name.isNotBlank(),
//                    onClick = { onAddClick() },
//                    shape = CircleShape,
//                    colors = ButtonDefaults.buttonColors(containerColor = Purple),
//                ) {
//                    Text(
//                        text = stringResource(R.string.add),
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.SemiBold,
//                    )
//                }
//            }
//        },
//    ) { contentPadding ->
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = contentPadding,
//            verticalArrangement = Arrangement.spacedBy(22.dp),
//        ) {
//            item {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .statusBarsPadding()
//                        .height(72.dp),
//                ) {
//                    PreviewIconButton(
//                        modifier = Modifier
//                            .align(Alignment.CenterStart)
//                            .padding(start = 20.dp),
//                        onClick = onBackClick,
//                        contentDescription = stringResource(R.string.back),
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = null,
//                        )
//                    }
//                }
//            }
//
//            item {
//                PhotoGrid(
//                    modifier = Modifier.padding(horizontal = 20.dp),
//                    photos = photoPlaceholders,
//                    onRemove = { photoPlaceholders = photoPlaceholders - it },
//                    onAdd = {
//                        if (photoPlaceholders.size < MaxPhotoCount) {
//                            photoPlaceholders = photoPlaceholders + "custom-photo-${nextPhotoId++}"
//                        }
//                    },
//                )
//            }
//
//            item {
//                Column(
//                    modifier = Modifier.padding(horizontal = 20.dp),
//                    verticalArrangement = Arrangement.spacedBy(22.dp),
//                ) {
//                    OutlinedTextField(
//                        modifier = Modifier.fillMaxWidth(),
//                        value = name,
//                        onValueChange = { state.name = it },
//                        singleLine = true,
//                        label = { Text(stringResource(R.string.name)) },
//                    )
//
//                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
//                        Text(
//                            text = stringResource(R.string.rating),
//                            style = MaterialTheme.typography.titleMedium,
//                        )
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(
//                                space = 4.dp,
//                                alignment = Alignment.CenterHorizontally,
//                            ),
//                        ) {
//                            (1..5).forEach { value ->
//                                IconButton(onClick = { state.rating = value }) {
//                                    Icon(
//                                        modifier = Modifier.size(46.dp),
//                                        imageVector = if (value <= state.rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
//                                        contentDescription = stringResource(R.string.rating_value, value),
//                                        tint = if (value <= state.rating) RatingStarColor else MaterialTheme.colorScheme.outline,
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    LabeledField(
//                        label = stringResource(R.string.tags),
//                        labelModifier = Modifier.bringIntoViewRequester(tagsBringIntoViewRequester),
//                    ) {
//                        if (selectedTags.isNotEmpty()) {
//                            FlowRow(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                verticalArrangement = Arrangement.spacedBy(8.dp),
//                            ) {
//                                selectedTags.forEach { tag ->
//                                    InputChip(
//                                        selected = true,
//                                        onClick = { selectedTags = selectedTags - tag },
//                                        shape = CircleShape,
//                                        label = { Text(tag) },
//                                        trailingIcon = {
//                                            Icon(
//                                                modifier = Modifier.size(18.dp),
//                                                imageVector = Icons.Filled.Close,
//                                                contentDescription = stringResource(R.string.remove_tag, tag),
//                                            )
//                                        },
//                                    )
//                                }
//                            }
//                        }
//
//                        OutlinedTextField(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .onFocusChanged { focusState ->
//                                    if (focusState.isFocused) {
//                                        coroutineScope.launch {
//                                            tagsBringIntoViewRequester.bringIntoView()
//                                        }
//                                    }
//                                },
//                            value = tagText,
//                            onValueChange = { tagText = it },
//                            singleLine = true,
//                            placeholder = { Text(stringResource(R.string.tags_hint)) },
//                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                            keyboardActions = KeyboardActions(onDone = { addTag() }),
//                            trailingIcon = {
//                                if (tagText.isNotEmpty()) {
//                                    IconButton(onClick = { tagText = "" }) {
//                                        Icon(
//                                            imageVector = Icons.Filled.Close,
//                                            contentDescription = stringResource(R.string.clear),
//                                        )
//                                    }
//                                }
//                            },
//                        )
//
//                        val query = tagText.trim()
//                        val suggestions = if (query.isEmpty()) {
//                            emptyList()
//                        } else {
//                            SuggestedTags.filter {
//                                it !in selectedTags && it.contains(query, ignoreCase = true)
//                            }
//                        }
//
//                        if (suggestions.isNotEmpty()) {
//                            FlowRow(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                verticalArrangement = Arrangement.spacedBy(8.dp),
//                            ) {
//                                suggestions.forEach { tag ->
//                                    TagSuggestion(
//                                        tag = tag,
//                                        selected = false,
//                                        onClick = { addTag(tag) },
//                                    )
//                                }
//                            }
//                        } else if (query.isNotEmpty() && query !in selectedTags) {
//                            OutlinedButton(onClick = { addTag(query) }) {
//                                Icon(
//                                    imageVector = Icons.Filled.Add,
//                                    contentDescription = null,
//                                )
//                                Text(
//                                    modifier = Modifier.padding(start = 8.dp),
//                                    text = stringResource(R.string.add_named_tag, query),
//                                )
//                            }
//                        }
//                    }
//
//                    LabeledField(label = stringResource(R.string.comment)) {
//                        OutlinedTextField(
//                            modifier = Modifier.fillMaxWidth(),
//                            value = state.comment,
//                            onValueChange = { state.comment = it },
//                            minLines = 4,
//                            maxLines = 7,
//                            placeholder = { Text(stringResource(R.string.comment_hint)) },
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//private val SuggestedTags = listOf("зелёный", "прозрачный", "драгоценный", "редкий")
//private val Purple = Color(0xFF7250B5)
//private val RatingStarColor = Color(0xFFFFB300)
//private const val PhotoColumnCount = 3
//private const val MaxPhotoCount = 6
//private val PhotoCellHeight = 112.dp
//private val PhotoGridSpacing = 8.dp
//
//@Preview(showBackground = true, heightDp = 900)
//@Composable
//private fun AddGemScreenPreview() {
//    GemologistTheme {
//        AddGemScreen(
//            AddGemState(
//                name = "Test"
//            ),
//            onBackClick = {},
//            onAddClick = {},
//        )
//    }
//}
