package ru.tdpyramid.gemologist.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.repository.GemDao

class GemService(
    private val gemDao: GemDao
) {
    fun observeAll() : Flow<List<Gem>> {
        return gemDao.observeAll().map {
            it.map { entity ->
                Gem(
                    id = entity.id,
                    name = entity.name,
                    isFavorite =  entity.isFavorite
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
                isFavorite = entity.isFavorite
            )
        }
    }
}