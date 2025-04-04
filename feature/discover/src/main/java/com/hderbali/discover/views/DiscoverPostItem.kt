package com.hderbali.discover.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.Post
import com.hderbali.model.User
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes
import com.hderbali.ui.theme.ExtendedTheme

@Composable
fun DiscoverPostItem(
    post: Post,
    user: User?,
    onClick: () -> Unit,
    onUserClick: () -> Unit
) {
    Card(
        modifier = Modifier.Companion
            .padding(4.dp)
            .clickable(onClick = onClick),
        shape = AppSpecificShapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (post.media.isNotEmpty()) {
                    val imageUrl = post.media.first() + "?w=800"
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Image du post",
                        contentScale = ContentScale.Companion.Crop,
                        modifier = Modifier.Companion.fillMaxSize()
                    )

                    if (post.type == "video") {
                        Box(
                            modifier = Modifier.Companion
                                .align(Alignment.Companion.Center)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Companion.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Vidéo",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.Companion.size(24.dp)
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(AppDimensions.spacing8),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(
                            text = post.content,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 5,
                            overflow = TextOverflow.Companion.Ellipsis,
                            textAlign = TextAlign.Companion.Center
                        )
                    }
                }
            }

            if (user != null) {
                Row(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(AppDimensions.spacing8)
                        .clickable(onClick = onUserClick),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.Companion
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        if (user.profilePicUrl.isNotEmpty()) {
                            val imageUrl = user.profilePicUrl + "?w=800"
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Photo miniature de ${user.displayName}",
                                contentScale = ContentScale.Companion.Crop,
                                modifier = Modifier.Companion.fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.Companion.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))

                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Companion.Ellipsis,
                        modifier = Modifier.Companion.weight(1f)
                    )

                    if (user.isVerified) {
                        Icon(
                            imageVector = Icons.Outlined.Verified,
                            contentDescription = "Compte vérifié",
                            tint = ExtendedTheme.verifiedColor,
                            modifier = Modifier.Companion.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}