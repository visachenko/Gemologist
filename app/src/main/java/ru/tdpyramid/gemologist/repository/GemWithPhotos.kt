package ru.tdpyramid.gemologist.repository

import androidx.room.Embedded
import androidx.room.Relation

data class GemWithPhotos(
    @Embedded
    val gem: GemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gemId",
    )
    val photos: List<GemPhotoEntity>,
)
