package ru.tdpyramid.gemologist.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val rating: Float = 0.0f,
    val isFavorite: Boolean = false,
    val comment: String = ""
)