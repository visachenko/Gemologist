package ru.tdpyramid.gemologist.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val rating: Float,
    val comment: String
)