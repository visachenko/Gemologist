package ru.tdpyramid.gemologist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.tdpyramid.gemologist.service.GemService
import ru.tdpyramid.gemologist.ui.GemologistApp
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gemologistApplication = application as GemologistApplication
        val gemService = GemService(
            gemDao = gemologistApplication.database.gemDao(),
            photoService = gemologistApplication.photoService,
        )
        setContent {
            GemologistTheme {
                GemologistApp(
                    gemService = gemService,
                    photoService = gemologistApplication.photoService,
                )
            }
        }
    }
}
