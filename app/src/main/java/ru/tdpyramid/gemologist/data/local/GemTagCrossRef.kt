package ru.tdpyramid.gemologist.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "gem_tags",
    primaryKeys = ["gemId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = GemEntity::class,
            parentColumns = ["id"],
            childColumns = ["gemId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("tagId")],
)
data class GemTagCrossRef(
    val gemId: Long,
    val tagId: Long,
)
