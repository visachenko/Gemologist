package ru.tdpyramid.gemologist.ui.state

import android.net.Uri

const val MAX_GEM_PHOTO_COUNT = 3

data class AddGemState(
    val name: String = "",
    val rating: Float = 0.0f,
    val comment: String = "",
    val isFavorite: Boolean = false,
    val photoUris: List<Uri> = emptyList(),
)
