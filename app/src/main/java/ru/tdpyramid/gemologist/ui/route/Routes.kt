package ru.tdpyramid.gemologist.ui.route

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object AddGemRoute

@Serializable
data class GemDetailsRoute(val gemId: Long)