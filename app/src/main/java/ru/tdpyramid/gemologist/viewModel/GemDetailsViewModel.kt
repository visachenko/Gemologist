package ru.tdpyramid.gemologist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.service.GemService

class GemDetailsViewModel(
    private val gemId: Long,
    private val gemService: GemService
) : ViewModel() {
    val uiState: StateFlow<Gem?> = gemService.observe(gemId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun setFavorite() {
        viewModelScope.launch {
            gemService.setFavorite(gemId)
        }
    }
}