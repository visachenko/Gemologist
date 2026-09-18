package ru.tdpyramid.gemologist.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.repository.GemDao
import ru.tdpyramid.gemologist.repository.GemEntity

class GemService(
    private val gemDao: GemDao
) {
    fun observeAll() : Flow<List<Gem>> {
        return gemDao.observeAll().map {
            it.map { entity ->
                Gem(
                    id = entity.id,
                    name = entity.name,
                    rating = entity.rating,
                    comment = entity.comment,
                    isFavorite = entity.isFavorite,
                )
            }
        }
    }

    suspend fun setFavorite(id: Long) {
        gemDao.setFavorite(id)
    }

    fun observe(gemId: Long): Flow<Gem> {
        return gemDao.observe(gemId).map { entity ->
            Gem(
                id = entity.id,
                name = entity.name,
                rating = entity.rating,
                comment = entity.comment,
                isFavorite = entity.isFavorite,
            )
        }
    }

    suspend fun addGem(
        name: String,
        rating: Float,
        comment: String,
        isFavorite: Boolean,
    ) {
        gemDao.insert(
            GemEntity(
                name = name,
                rating = rating,
                comment = comment,
                isFavorite = isFavorite,
            )
        )
    }
}
