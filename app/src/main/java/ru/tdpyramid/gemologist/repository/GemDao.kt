package ru.tdpyramid.gemologist.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GemDao {
    @Query("SELECT * FROM gemEntity")
    fun observeAll(): Flow<List<GemEntity>>

    @Query("SELECT * FROM gementity WHERE id = :id")
    fun observe(id: Long): Flow<GemEntity>

    @Insert
    suspend fun insert(gemEntity: GemEntity)

    @Query("UPDATE gementity SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long)
}