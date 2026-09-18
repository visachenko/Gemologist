package ru.tdpyramid.gemologist.data

data class Gem(
    val id: Long,
    val name: String,
    val rating: Float,
    val comment: String,
    val isFavorite: Boolean,
)
