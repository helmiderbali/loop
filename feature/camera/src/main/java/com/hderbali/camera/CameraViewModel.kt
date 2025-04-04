package com.hderbali.camera

// feature-camera module

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.ui.usescases.post.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CameraScreenState())
    val state: StateFlow<CameraScreenState> = _state.asStateFlow()

    fun onCameraPermissionGranted() {
        _state.update { it.copy(hasCameraPermission = true) }
    }

    fun toggleFlash() {
        _state.update { it.copy(flashEnabled = !it.flashEnabled) }
    }

    fun toggleCamera() {
        _state.update { it.copy(
            cameraSelector = if (state.value.cameraSelector == CameraSelector.LENS_FACING_BACK) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
        )}
    }

    fun onCaptureStarted() {
        _state.update { it.copy(isCapturing = true) }
    }

    fun onImageCaptured(imageFile: File) {
        val uri =
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                imageFile
            )

        _state.update {
            it.copy(
                isCapturing = false,
                capturedImageUri = uri,
                imageFile = imageFile
            )
        }

        addImageToGallery(imageFile)
    }

    fun updateCaption(caption: String) {
        _state.update { it.copy(postCaption = caption) }
    }

    fun updateTags(tags: String) {
        _state.update { it.copy(postTags = tags) }
    }

    fun updateLocation(location: String) {
        _state.update { it.copy(location = location) }
    }

    fun resetCaptureState() {
        _state.update {
            it.copy(
                capturedImageUri = null,
                imageFile = null,
                postCaption = "",
                postTags = "",
                location = "",
                isPosting = false,
                postingError = null,
                postSuccess = false
            )
        }
    }

    fun createPost() {
        val imageUri = state.value.capturedImageUri ?: return
        val caption = state.value.postCaption.trim()
        val tags = state.value.postTags
            .split(",", " ", "#")
            .filter { it.isNotBlank() }
            .map { it.trim().replace("#", "") }
        val location = state.value.location.trim()

        _state.update { it.copy(isPosting = true, postingError = null) }

        viewModelScope.launch {
            try {
                val post = Post(
                    id = UUID.randomUUID().toString(),
                    userId = "user10",
                    type = "photo",
                    content = caption,
                    media = listOf(imageUri.toString()),
                    timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                        .format(Date()),
                    likeCount = 0,
                    commentCount = 0,
                    viewCount = 0,
                    location = if (location.isNotEmpty()) location else null,
                    tags = tags,
                    isTrending = false,
                    isBookmarked = false,
                    isLiked = false
                )

                // Envoyer le post au repository via le use case
                createPostUseCase(post).collect { result ->
                    when (result) {
                        is ResultOf.Success -> {
                            _state.update {
                                it.copy(
                                    isPosting = false,
                                    postSuccess = true
                                )
                            }
                        }
                        is ResultOf.Error -> {
                            _state.update {
                                it.copy(
                                    isPosting = false,
                                    postingError = result.exception.message ?: "Erreur lors de la publication du post"
                                )
                            }
                        }
                        ResultOf.Loading -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isPosting = false,
                        postingError = e.message ?: "Une erreur s'est produite lors de la publication"
                    )
                }
            }
        }
    }

    private fun addImageToGallery(imageFile: File) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let {
                    context.contentResolver.openOutputStream(it)?.use { outputStream ->
                        imageFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(it, contentValues, null, null)
                }
            } else {
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(imageFile.absolutePath),
                    arrayOf("image/jpeg"),
                    null
                )
            }
        } catch (e: Exception) {
        }
    }

    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }
}