package ru.tdpyramid.gemologist.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.tdpyramid.gemologist.domain.Gem

data class GemWithTags(
    @Embedded val gem: GemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = GemTagCrossRef::class,
            parentColumn = "gemId",
            entityColumn = "tagId",
        ),
    )
    val tags: List<TagEntity>,
)

fun GemWithTags.toDomain(): Gem = Gem(
    id = gem.id,
    name = gem.name,
    rating = gem.rating,
    tags = tags.map(TagEntity::name),
    comment = gem.comment,
    isFavorite = gem.isFavorite,
)
