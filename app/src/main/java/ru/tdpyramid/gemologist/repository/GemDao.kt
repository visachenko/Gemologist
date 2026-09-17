package ru.tdpyramid.gemologist.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GemDao {
    @Query("SELECT * FROM gemEntity")
    suspend fun getAll(): List<GemEntity>

    @Insert
    suspend fun insert(gemEntity: GemEntity)
}