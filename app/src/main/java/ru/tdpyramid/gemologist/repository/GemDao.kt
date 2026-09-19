package ru.tdpyramid.gemologist.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GemDao {
    @Transaction
    @Query("SELECT * FROM gemEntity")
    fun observeAll(): Flow<List<GemWithPhotos>>

    @Transaction
    @Query("SELECT * FROM gementity WHERE id = :id")
    fun observe(id: Long): Flow<GemWithPhotos>

    @Insert
    suspend fun insert(gemEntity: GemEntity): Long

    @Insert
    suspend fun insertPhotos(photos: List<GemPhotoEntity>)

    @Transaction
    suspend fun insert(gemEntity: GemEntity, photoFileNames: List<String>) {
        val gemId = insert(gemEntity)
        insertPhotos(
            photoFileNames.mapIndexed { position, fileName ->
                GemPhotoEntity(
                    gemId = gemId,
                    fileName = fileName,
                    position = position,
                )
            },
        )
    }

    @Query("SELECT fileName FROM GemPhotoEntity")
    suspend fun getAllPhotoFileNames(): List<String>

    @Query("UPDATE gementity SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long)
}
