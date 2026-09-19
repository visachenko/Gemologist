package ru.tdpyramid.gemologist.repository

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GemEntity::class,
            parentColumns = ["id"],
            childColumns = ["gemId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("gemId")],
)
data class GemPhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gemId: Long,
    val fileName: String,
    val position: Int,
)
