package ru.tdpyramid.gemologist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GemDao {
    @Transaction
    @Query("SELECT * FROM gems ORDER BY createdAt")
    fun observeAll(): Flow<List<GemWithTags>>

    @Query("SELECT COUNT(*) FROM gems")
    suspend fun count(): Int

    @Insert
    suspend fun insertGem(gem: GemEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tag: TagEntity): Long

    @Query("SELECT id FROM tags WHERE name = :name")
    suspend fun findTagId(name: String): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGemTag(crossRef: GemTagCrossRef)

    @Transaction
    suspend fun insertGemWithTags(gem: GemEntity, tagNames: List<String>) {
        val gemId = insertGem(gem)
        tagNames.forEach { name ->
            val insertedTagId = insertTag(TagEntity(name = name))
            val tagId = if (insertedTagId == -1L) {
                requireNotNull(findTagId(name))
            } else {
                insertedTagId
            }
            insertGemTag(GemTagCrossRef(gemId = gemId, tagId = tagId))
        }
    }

    @Query("UPDATE gems SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long)
}
