package ru.tdpyramid.gemologist

import android.app.Application
import androidx.room.Room
import ru.tdpyramid.gemologist.repository.GemDatabase

class GemologistApplication : Application() {
    val database: GemDatabase by lazy {
        Room.inMemoryDatabaseBuilder<GemDatabase>(this)
            .build()
    }
}