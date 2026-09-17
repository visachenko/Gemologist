package ru.tdpyramid.gemologist.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GemEntity::class],
    version = 1
)
abstract class GemDatabase : RoomDatabase() {
    abstract fun gemDao(): GemDao
}