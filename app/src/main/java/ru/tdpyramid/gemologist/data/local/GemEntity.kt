package ru.tdpyramid.gemologist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tdpyramid.gemologist.domain.Gem

@Entity(tableName = "gems")
data class GemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val rating: Float,
    val comment: String,
    val isFavorite: Boolean,
    val createdAt: Long,
)

fun Gem.toEntity(createdAt: Long): GemEntity = GemEntity(
    id = id,
    name = name,
    rating = rating,
    comment = comment,
    isFavorite = isFavorite,
    createdAt = createdAt,
)
