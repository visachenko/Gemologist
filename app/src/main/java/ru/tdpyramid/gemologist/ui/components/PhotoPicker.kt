package ru.tdpyramid.gemologist.ui.components

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.yalantis.ucrop.UCrop

@Composable
fun PhotoPicker(
    photos: List<Uri>,
    createCropDestination: () -> Uri,
    onPhotoCropped: (Uri) -> Unit,
    onCropCancelled: (Uri) -> Unit,
    onPhotoRemove: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var pendingCropDestination by rememberSaveable { mutableStateOf<String?>(null) }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val destination = pendingCropDestination?.let(Uri::parse)
        val croppedUri = result.data?.let(UCrop::getOutput)

        if (result.resultCode == Activity.RESULT_OK && croppedUri != null) {
            onPhotoCropped(croppedUri)
        } else if (destination != null) {
            onCropCancelled(destination)
        }
        pendingCropDestination = null
    }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { sourceUri ->
        if (sourceUri == null) return@rememberLauncherForActivityResult

        val destinationUri = createCropDestination()
        pendingCropDestination = destinationUri.toString()
        val cropIntent = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(MAX_CROPPED_PHOTO_SIZE, MAX_CROPPED_PHOTO_SIZE)
            .getIntent(context)
        cropLauncher.launch(cropIntent)
    }

    PhotoGrid(
        photos = photos,
        onRemove = onPhotoRemove,
        onAdd = {
            photoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
            )
        },
        modifier = modifier,
    )
}

private const val MAX_CROPPED_PHOTO_SIZE = 1600
