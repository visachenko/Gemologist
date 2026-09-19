package ru.tdpyramid.gemologist.service

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class PhotoService(
    private val context: Context,
) {
    fun clearAllPhotos() {
        File(context.cacheDir, CROP_DIRECTORY).deleteRecursively()
        File(context.filesDir, PHOTO_DIRECTORY).deleteRecursively()
    }

    fun createCropDestination(): Uri {
        val directory = File(context.cacheDir, CROP_DIRECTORY).apply(File::mkdirs)
        val file = File(directory, "${UUID.randomUUID()}.jpg")
        return file.toContentUri()
    }

    suspend fun preparePhoto(croppedUri: Uri): Uri = withContext(Dispatchers.IO) {
        val source = croppedUri.fileIn(context.cacheDir, CROP_DIRECTORY)
        check(source.isFile && source.length() > 0L) { "Cropped photo is empty" }

        val directory = File(context.filesDir, PHOTO_DIRECTORY).apply(File::mkdirs)
        val destination = File(directory, "${UUID.randomUUID()}.jpg")
        source.inputStream().use { inputStream ->
            destination.outputStream().use(inputStream::copyTo)
        }
        source.delete()

        destination.toContentUri()
    }

    fun discardCrop(croppedUri: Uri) {
        croppedUri.fileIn(context.cacheDir, CROP_DIRECTORY).delete()
    }

    fun deletePhoto(photoUri: Uri) {
        photoUri.fileIn(context.filesDir, PHOTO_DIRECTORY).delete()
    }

    fun getFileName(photoUri: Uri): String =
        photoUri.fileIn(context.filesDir, PHOTO_DIRECTORY).name

    fun getPhotoUri(fileName: String): Uri {
        require(fileName.matches(FILE_NAME_PATTERN)) { "Invalid photo file name: $fileName" }
        return File(File(context.filesDir, PHOTO_DIRECTORY), fileName).toContentUri()
    }

    suspend fun cleanupFiles(referencedFileNames: Set<String>) = withContext(Dispatchers.IO) {
        val staleBefore = System.currentTimeMillis() - STALE_FILE_AGE_MS

        File(context.cacheDir, CROP_DIRECTORY)
            .listFiles()
            .orEmpty()
            .filter { it.lastModified() < staleBefore }
            .forEach(File::delete)

        File(context.filesDir, PHOTO_DIRECTORY)
            .listFiles()
            .orEmpty()
            .filter { it.name !in referencedFileNames && it.lastModified() < staleBefore }
            .forEach(File::delete)
    }

    private fun Uri.fileIn(root: File, directory: String): File {
        val fileName = requireNotNull(lastPathSegment).substringAfterLast('/')
        require(fileName.matches(FILE_NAME_PATTERN)) { "Invalid photo URI: $this" }
        return File(File(root, directory), fileName)
    }

    private fun File.toContentUri(): Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        this,
    )

    private companion object {
        const val CROP_DIRECTORY = "photo_crop"
        const val PHOTO_DIRECTORY = "photos"
        const val STALE_FILE_AGE_MS = 24 * 60 * 60 * 1000L
        val FILE_NAME_PATTERN = Regex("[0-9a-f-]+\\.jpg")
    }
}
