package ru.tdpyramid.gemologist.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tdpyramid.gemologist.data.Gem
import ru.tdpyramid.gemologist.repository.GemDao
import ru.tdpyramid.gemologist.repository.GemEntity
import ru.tdpyramid.gemologist.repository.GemWithPhotos

class GemService(
    private val gemDao: GemDao,
    private val photoService: PhotoService,
) {
    fun observeAll() : Flow<List<Gem>> {
        return gemDao.observeAll().map {
            it.map(::toGem)
        }
    }

    suspend fun setFavorite(id: Long) {
        gemDao.setFavorite(id)
    }

    fun observe(gemId: Long): Flow<Gem> {
        return gemDao.observe(gemId).map(::toGem)
    }

    suspend fun addGem(
        name: String,
        rating: Float,
        comment: String,
        isFavorite: Boolean,
        photoUris: List<android.net.Uri>,
    ) {
        gemDao.insert(
            GemEntity(
                name = name,
                rating = rating,
                comment = comment,
                isFavorite = isFavorite,
            ),
            photoUris.map(photoService::getFileName),
        )
    }

    private fun toGem(item: GemWithPhotos): Gem = Gem(
        id = item.gem.id,
        name = item.gem.name,
        rating = item.gem.rating,
        comment = item.gem.comment,
        isFavorite = item.gem.isFavorite,
        photoUris = item.photos
            .sortedBy { it.position }
            .map { photoService.getPhotoUri(it.fileName) },
    )
}
