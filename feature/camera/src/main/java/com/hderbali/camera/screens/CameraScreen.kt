package com.hderbali.camera.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.hderbali.camera.CameraViewModel
import com.hderbali.ui.theme.AppDimensions
import kotlinx.coroutines.delay
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    onNavigateBack: () -> Unit,
    onPostSuccess: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.onCameraPermissionGranted()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) {
            viewModel.onCameraPermissionGranted()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(state.postingError) {
        state.postingError?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(state.postSuccess) {
        if (state.postSuccess) {
            delay(500)
            onPostSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!state.hasCameraPermission) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(AppDimensions.spacing16)
                    ) {
                        Text(
                            text = "Permission de caméra requise",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(AppDimensions.spacing16))
                        Text(
                            text = "Veuillez accorder la permission d'accès à la caméra pour prendre des photos.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(AppDimensions.spacing16))
                        Button(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) {
                            Text("Accorder la permission")
                        }
                    }
                }
            } else if (state.capturedImageUri == null) {
                CameraContent(
                    onImageCaptured = viewModel::onImageCaptured,
                    onCaptureStarted = viewModel::onCaptureStarted,
                    onNavigateBack = onNavigateBack,
                    onToggleFlash = viewModel::toggleFlash,
                    onToggleCamera = viewModel::toggleCamera,
                    isCapturing = state.isCapturing,
                    flashEnabled = state.flashEnabled,
                    cameraSelector = state.cameraSelector,
                    createImageFile = viewModel::createImageFile
                )
            } else {
                PostEditScreen(
                    capturedImageUri = state.capturedImageUri,
                    onCaptionChanged = viewModel::updateCaption,
                    onTagsChanged = viewModel::updateTags,
                    onLocationChanged = viewModel::updateLocation,
                    onRetakePhoto = viewModel::resetCaptureState,
                    onPost = viewModel::createPost,
                    caption = state.postCaption,
                    tags = state.postTags,
                    location = state.location,
                    isPosting = state.isPosting
                )
            }
        }
    }
}

@Composable
private fun CameraContent(
    onImageCaptured: (File) -> Unit,
    onCaptureStarted: () -> Unit,
    onNavigateBack: () -> Unit,
    onToggleFlash: () -> Unit,
    onToggleCamera: () -> Unit,
    isCapturing: Boolean,
    flashEnabled: Boolean,
    cameraSelector: Int,
    createImageFile: () -> File
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    var preview by remember { mutableStateOf<Preview?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        LaunchedEffect(cameraSelector, flashEnabled) {
            bindCameraUseCases(
                context = context,
                lifecycleOwner = lifecycleOwner,
                cameraSelector = cameraSelector,
                previewView = previewView,
                flashEnabled = flashEnabled,
                onPreviewReady = { preview = it },
                onImageCaptureReady = { imageCapture = it }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(AppDimensions.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.spacing16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }

                Row {
                    IconButton(
                        onClick = onToggleFlash,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (flashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Flash",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(AppDimensions.spacing12))

                    IconButton(
                        onClick = onToggleCamera,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flip,
                            contentDescription = "Changer de caméra",
                            tint = Color.White
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimensions.spacing32),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            border = BorderStroke(4.dp, Color.White),
                            shape = CircleShape
                        )
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = if (isCapturing) 0.3f else 1f))
                        .clickable(enabled = !isCapturing) {
                            takePicture(
                                imageCapture = imageCapture,
                                executor = ContextCompat.getMainExecutor(context),
                                onCaptureStarted = onCaptureStarted,
                                onImageCaptured = onImageCaptured,
                                createImageFile = createImageFile
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isCapturing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

private fun takePicture(
    imageCapture: ImageCapture?,
    executor: Executor,
    onCaptureStarted: () -> Unit,
    onImageCaptured: (File) -> Unit,
    createImageFile: () -> File
) {
    imageCapture?.let {
        onCaptureStarted()

        val outputFile = createImageFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

        it.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onImageCaptured(outputFile)
                }

                override fun onError(exception: ImageCaptureException) {
                }
            }
        )
    }
}

private suspend fun bindCameraUseCases(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: Int,
    previewView: PreviewView,
    flashEnabled: Boolean,
    onPreviewReady: (Preview) -> Unit,
    onImageCaptureReady: (ImageCapture) -> Unit
) = suspendCoroutine { continuation ->
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageCaptureBuilder = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)

        if (flashEnabled) {
            imageCaptureBuilder.setFlashMode(ImageCapture.FLASH_MODE_ON)
        } else {
            imageCaptureBuilder.setFlashMode(ImageCapture.FLASH_MODE_OFF)
        }

        val imageCapture = imageCaptureBuilder.build()

        val selector = CameraSelector.Builder()
            .requireLensFacing(cameraSelector)
            .build()

        try {
            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageCapture
            )

            onPreviewReady(preview)
            onImageCaptureReady(imageCapture)

            continuation.resume(Unit)
        } catch (e: Exception) {
            continuation.resume(Unit)
        }
    }, ContextCompat.getMainExecutor(context))
}