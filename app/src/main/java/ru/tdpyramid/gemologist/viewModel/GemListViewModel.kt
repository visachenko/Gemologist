package ru.tdpyramid.gemologist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.service.GemService

class GemListViewModel(
    private val gemService: GemService
) : ViewModel() {
    val uiState: StateFlow<List<Gem>> = gemService.observeAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onFavoriteClick(id: Long) {
        viewModelScope.launch {
            gemService.setFavorite(id)
        }
    }
}