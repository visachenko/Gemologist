package ru.tdpyramid.gemologist.ui.state

data class AddGemState(
    val name: String = "",
    val rating: Float = 0.0f,
    val comment: String = "",
    val isFavorite: Boolean = false,
)
