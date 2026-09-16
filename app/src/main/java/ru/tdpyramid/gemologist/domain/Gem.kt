package ru.tdpyramid.gemologist.domain

data class Gem(
    val id: Long = 0,
    val name: String,
    val rating: Float,
    val tags: List<String> = emptyList(),
    val comment: String = "",
    val isFavorite: Boolean = false,
)
