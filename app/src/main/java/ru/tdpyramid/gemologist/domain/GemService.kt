package ru.tdpyramid.gemologist.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tdpyramid.gemologist.data.local.GemDao
import ru.tdpyramid.gemologist.data.local.toDomain
import ru.tdpyramid.gemologist.data.local.toEntity

class GemService(
    private val dao: GemDao,
) {
    fun observeGems(): Flow<List<Gem>> = dao.observeAll().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun addGem(
        name: String,
        rating: Float,
        tags: List<String>,
        comment: String,
    ) {
        require(name.isNotBlank()) { "Название не может быть пустым" }

        val gem = Gem(
            name = name.trim(),
            rating = rating.coerceIn(0f, 5f),
            tags = tags.map(String::trim).filter(String::isNotEmpty).distinct(),
            comment = comment.trim(),
        )
        dao.insertGemWithTags(
            gem = gem.toEntity(createdAt = System.currentTimeMillis()),
            tagNames = gem.tags,
        )
    }

    suspend fun toggleFavorite(id: Long) {
        dao.toggleFavorite(id)
    }

    suspend fun seedIfEmpty(gems: List<Gem>) {
        if (dao.count() == 0) {
            val firstCreatedAt = System.currentTimeMillis()
            gems.forEachIndexed { index, gem ->
                dao.insertGemWithTags(
                    gem = gem.toEntity(createdAt = firstCreatedAt + index),
                    tagNames = gem.tags,
                )
            }
        }
    }
}
