package ru.tdpyramid.gemologist

import android.app.Application
import androidx.room.Room
import ru.tdpyramid.gemologist.repository.GemDatabase
import ru.tdpyramid.gemologist.service.PhotoService

class GemologistApplication : Application() {
    val photoService: PhotoService by lazy { PhotoService(this) }

    val database: GemDatabase by lazy {
        Room.databaseBuilder<GemDatabase>(
            context = this,
            name = DATABASE_NAME,
        ).build()
    }

    private companion object {
        const val DATABASE_NAME = "gemologist.db"
    }
}
