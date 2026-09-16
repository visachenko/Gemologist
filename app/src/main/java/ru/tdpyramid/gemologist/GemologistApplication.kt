package ru.tdpyramid.gemologist

import android.app.Application
import androidx.room.Room
import ru.tdpyramid.gemologist.data.local.GemDatabase
import ru.tdpyramid.gemologist.domain.GemService

class GemologistApplication : Application() {
    private val database: GemDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            GemDatabase::class.java,
            "gemologist.db",
        ).build()
    }

    val gemService: GemService by lazy {
        GemService(database.gemDao())
    }
}
