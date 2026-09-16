package ru.tdpyramid.gemologist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import ru.tdpyramid.gemologist.ui.GemologistApp
import ru.tdpyramid.gemologist.ui.GemologistViewModel
import ru.tdpyramid.gemologist.ui.theme.GemologistTheme

class MainActivity : ComponentActivity() {
    private val viewModel: GemologistViewModel by viewModels {
        GemologistViewModel.factory(
            (application as GemologistApplication).gemService,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GemologistTheme {
                GemologistApp(viewModel = viewModel)
            }
        }
    }
}
