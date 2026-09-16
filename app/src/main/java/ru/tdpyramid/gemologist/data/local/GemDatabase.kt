package ru.tdpyramid.gemologist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GemEntity::class, TagEntity::class, GemTagCrossRef::class],
    version = 1,
    exportSchema = false,
)
abstract class GemDatabase : RoomDatabase() {
    abstract fun gemDao(): GemDao
}
