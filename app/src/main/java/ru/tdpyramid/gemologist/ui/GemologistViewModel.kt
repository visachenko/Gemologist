package ru.tdpyramid.gemologist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tdpyramid.gemologist.domain.Gem
import ru.tdpyramid.gemologist.domain.GemService
import ru.tdpyramid.gemologist.ui.data.SampleGemItems

class GemologistViewModel(
    private val gemService: GemService,
) : ViewModel() {
    val gems: StateFlow<List<Gem>> = gemService.observeGems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    init {
        viewModelScope.launch {
            gemService.seedIfEmpty(SampleGemItems)
        }
    }

    fun addGem(
        name: String,
        rating: Float,
        tags: List<String>,
        comment: String,
    ) {
        viewModelScope.launch {
            gemService.addGem(name, rating, tags, comment)
        }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch {
            gemService.toggleFavorite(id)
        }
    }

    companion object {
        fun factory(gemService: GemService): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return GemologistViewModel(gemService) as T
                }
            }
    }
}
