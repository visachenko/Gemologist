package ru.tdpyramid.gemologist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.tdpyramid.gemologist.service.GemService
import ru.tdpyramid.gemologist.service.PhotoService
import ru.tdpyramid.gemologist.ui.route.AddGemRoute
import ru.tdpyramid.gemologist.ui.route.GemDetailsRoute
import ru.tdpyramid.gemologist.ui.route.HomeRoute
import ru.tdpyramid.gemologist.viewModel.AddGemViewModel
import ru.tdpyramid.gemologist.viewModel.GemDetailsViewModel
import ru.tdpyramid.gemologist.viewModel.GemListViewModel

@Composable
fun GemologistApp(
    gemService: GemService,
    photoService: PhotoService,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> { backStackEntry ->
            val gemListViewModel: GemListViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                key = "gem-list",
                factory = viewModelFactory {
                    initializer {
                        GemListViewModel(
                            gemService = gemService
                        )
                    }
                }
            )
            val gems by gemListViewModel.uiState.collectAsStateWithLifecycle()
            GemListScreen(
                gems = gems,
                onItemClick = {
                    navController.navigate(GemDetailsRoute(it.id))
                },
                onFavoriteClick = {
                    gemListViewModel.onFavoriteClick(it)
                },
                onAddClick = { navController.navigate(AddGemRoute) },
            )
        }
        composable<GemDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<GemDetailsRoute>()
            val gemDetailsViewModel: GemDetailsViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                key = "gem-details-${route.gemId}",
                factory = viewModelFactory {
                    initializer {
                        GemDetailsViewModel(
                            gemId = route.gemId,
                            gemService = gemService
                        )
                    }
                }
            )
            val gem by gemDetailsViewModel.uiState.collectAsStateWithLifecycle()
            GemDetailsScreen(
                gem = gem,
                {
                    navController.navigateUp()
                },
                {
                    gemDetailsViewModel.setFavorite()
                }
            )
        }
        composable<AddGemRoute> { backStackEntry ->
            val addGemViewModel: AddGemViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                key = "add-gem",
                factory = viewModelFactory {
                    initializer {
                        AddGemViewModel(
                            gemService = gemService,
                            photoService = photoService,
                        )
                    }
                }
            )
            AddGemScreen(
                state = addGemViewModel.uiState,
                onNameChange = addGemViewModel::onNameChange,
                onRatingChange = addGemViewModel::onRatingChange,
                onCommentChange = addGemViewModel::onCommentChange,
                onFavoriteClick = addGemViewModel::onFavoriteClick,
                createCropDestination = addGemViewModel::createCropDestination,
                onPhotoCropped = addGemViewModel::onPhotoCropped,
                onCropCancelled = addGemViewModel::onCropCancelled,
                onPhotoRemove = addGemViewModel::onPhotoRemove,
                onBackClick = {
                    addGemViewModel.discardPhotos()
                    navController.navigateUp()
                },
                onAddClick = {
                    addGemViewModel.addGem {
                        navController.navigateUp()
                    }
                },
            )
        }
    }
}
