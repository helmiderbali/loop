package com.hderbali.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import java.io.File

data class CameraScreenState(
    val isCapturing: Boolean = false,
    val flashEnabled: Boolean = false,
    val capturedImageUri: Uri? = null,
    val cameraSelector: Int = CameraSelector.LENS_FACING_BACK,
    val postCaption: String = "",
    val postTags: String = "",
    val location: String = "",
    val isPosting: Boolean = false,
    val postingError: String? = null,
    val postSuccess: Boolean = false,
    val imageFile: File? = null,
    val hasCameraPermission: Boolean = false
)
