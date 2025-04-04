package com.hderbali.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.Post
import com.hderbali.ui.theme.AppDimensions

@Composable
fun PostGridItem(
    post: Post,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .aspectRatio(1f)
            .padding(1.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        if (post.media.isNotEmpty()) {
            val imageUrl = post.media.first() + "?w=800"
            AsyncImage(
                model = imageUrl,
                contentDescription = "Miniature du post",
                contentScale = ContentScale.Companion.Crop,
                modifier = Modifier.Companion.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(AppDimensions.spacing8),
                contentAlignment = Alignment.Companion.Center
            ) {
                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Companion.Ellipsis,
                    textAlign = TextAlign.Companion.Center
                )
            }
        }

        if (post.type == "video") {
            Box(
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .padding(AppDimensions.spacing4)
                    .size(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Companion.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Vidéo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.Companion.size(16.dp)
                )
            }
        }
    }
}