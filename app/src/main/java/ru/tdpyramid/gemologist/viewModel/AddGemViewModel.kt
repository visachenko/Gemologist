package ru.tdpyramid.gemologist.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tdpyramid.gemologist.service.GemService
import ru.tdpyramid.gemologist.ui.state.AddGemState

class AddGemViewModel(
    private val gemService: GemService
) : ViewModel() {
    var uiState by mutableStateOf(AddGemState())
        private set

    fun onNameChange(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onRatingChange(rating: Float) {
        uiState = uiState.copy(rating = rating)
    }

    fun onCommentChange(comment: String) {
        uiState = uiState.copy(comment = comment)
    }

    fun onFavoriteClick() {
        uiState = uiState.copy(isFavorite = !uiState.isFavorite)
    }

    fun addGem(onAdded: () -> Unit) {
        if (uiState.name.isBlank()) return

        viewModelScope.launch {
            gemService.addGem(
                name = uiState.name.trim(),
                rating = uiState.rating,
                comment = uiState.comment.trim(),
                isFavorite = uiState.isFavorite,
            )
            onAdded()
        }
    }
}
