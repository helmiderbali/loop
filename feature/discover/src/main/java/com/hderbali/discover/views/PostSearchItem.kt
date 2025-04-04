package com.hderbali.discover.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.Post
import com.hderbali.ui.theme.AppDimensions

@Composable
fun PostSearchItem(
    post: Post,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        // Miniature du post
        Box(
            modifier = Modifier.Companion
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Companion.Center
        ) {
            if (post.media.isNotEmpty()) {
                val imageUrl = post.media.first() + "?w=800"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Miniature du post",
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )

                if (post.type == "video") {
                    Box(
                        modifier = Modifier.Companion
                            .align(Alignment.Companion.Center)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
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
            } else {
                Icon(
                    imageVector = when (post.type) {
                        "music" -> Icons.Default.PlayArrow
                        "camera" -> Icons.Default.Camera
                        else -> Icons.Default.Search
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.Companion.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing12))

        Column(modifier = Modifier.Companion.weight(1f)) {
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Companion.Ellipsis
            )

            if (post.tags.isNotEmpty()) {
                Text(
                    text = post.tags.take(3).joinToString(" ") { "#$it" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )
            }
        }
    }
}