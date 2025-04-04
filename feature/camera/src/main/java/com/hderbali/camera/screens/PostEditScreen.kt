package com.hderbali.camera.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hderbali.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEditScreen(
    capturedImageUri: Uri?,
    onCaptionChanged: (String) -> Unit,
    onTagsChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRetakePhoto: () -> Unit,
    onPost: () -> Unit,
    caption: String,
    tags: String,
    location: String,
    isPosting: Boolean
) {
    Column(modifier = Modifier.Companion.fillMaxSize()) {
        TopAppBar(
            title = { Text("New post") },
            navigationIcon = {
                IconButton(onClick = onRetakePhoto) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Annuler"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onPost,
                    enabled = !isPosting
                ) {
                    if (isPosting) {
                        CircularProgressIndicator(
                            modifier = Modifier.Companion.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Publier"
                        )
                    }
                }
            }
        )

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(AppDimensions.spacing16)
        ) {
            capturedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Image capturée",
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Companion.Crop
                )
            }

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing16))

            OutlinedTextField(
                value = caption,
                onValueChange = onCaptionChanged,
                label = { Text("Legend") },
                placeholder = { Text("Add legend...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth(),
                maxLines = 5
            )

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing12))

            OutlinedTextField(
                value = tags,
                onValueChange = onTagsChanged,
                label = { Text("Tags") },
                placeholder = { Text("Add tags seperated by commas...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Tag,
                        contentDescription = null
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth()
            )

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing12))

            OutlinedTextField(
                value = location,
                onValueChange = onLocationChanged,
                label = { Text("Localisation") },
                placeholder = { Text("Add localisation...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth()
            )

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing24))

            Button(
                onClick = onPost,
                enabled = !isPosting,
                modifier = Modifier.Companion.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = AppDimensions.spacing16)
            ) {
                if (isPosting) {
                    CircularProgressIndicator(
                        modifier = Modifier.Companion.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Publier")
                }
            }
        }
    }
}