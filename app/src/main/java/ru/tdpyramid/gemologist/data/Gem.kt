package ru.tdpyramid.gemologist.data

import android.net.Uri

data class Gem(
    val id: Long,
    val name: String,
    val rating: Float,
    val comment: String,
    val isFavorite: Boolean,
    val photoUris: List<Uri> = emptyList(),
)
