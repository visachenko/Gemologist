package ru.tdpyramid.gemologist

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tdpyramid.gemologist.repository.GemDatabase
import ru.tdpyramid.gemologist.repository.GemEntity
import kotlin.random.Random

class GemologistApplication : Application() {
    val database: GemDatabase by lazy {
        val scope = CoroutineScope(Dispatchers.IO)
        Room.inMemoryDatabaseBuilder<GemDatabase>(this)
            .build().apply {
                scope.launch {
                    gemDao().apply {
                        insert(GemEntity(name ="Test", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                        insert(GemEntity(name ="Test1", rating = Random.nextFloat() * 5f))
                    }
                }
            }
    }
}
