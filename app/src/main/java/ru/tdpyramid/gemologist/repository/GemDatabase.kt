package ru.tdpyramid.gemologist.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GemEntity::class, GemPhotoEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class GemDatabase : RoomDatabase() {
    abstract fun gemDao(): GemDao
}
