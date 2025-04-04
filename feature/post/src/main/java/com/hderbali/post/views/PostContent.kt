package com.hderbali.post.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.Post
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes
import com.hderbali.ui.theme.ExtendedTheme
import com.yourapp.core.ui.theme.AppTextStyles

@Composable
fun PostContent(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16)
    ) {
        if (post.content.isNotEmpty()) {
            Text(
                text = post.content,
                style = AppTextStyles.PostContent,
                modifier = Modifier.padding(bottom = AppDimensions.spacing12)
            )
        }

        if (post.media.isNotEmpty()) {
            when (post.type) {
                "photo", "gallery" -> {
                    post.media.forEachIndexed { index, mediaUrl ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = if (index < post.media.size - 1) AppDimensions.spacing8 else 0.dp)
                        ) {
                            val imageUrl = mediaUrl + "?w=800"

                            AsyncImage(
                                model = mediaUrl,
                                contentDescription = "Image ${index + 1} du post",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(AppSpecificShapes.ImageShape)
                            )
                        }
                    }
                }
                "video" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val imageUrl = post.media.getOrNull(1) ?: (post.media.first() + "?w=800")
                        AsyncImage(
                            model = imageUrl, // Utiliser la miniature si disponible
                            contentDescription = "Miniature vidéo",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.78f) // Ratio 16:9
                                .clip(AppSpecificShapes.ImageShape)
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Lire la vidéo",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
                "music" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimensions.spacing12)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = AppSpecificShapes.CardShape
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimensions.spacing16),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageUrl = post.media.first() + "?w=800"

                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Couverture album",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(AppSpecificShapes.ImageShape)
                            )

                            Spacer(modifier = Modifier.width(AppDimensions.spacing16))

                            // Informations sur le morceau
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Écouter sur Spotify",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = post.spotifyLink ?: "Nouvelle piste musicale",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Lire l'extrait",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                "link" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimensions.spacing12)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = AppSpecificShapes.CardShape
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimensions.spacing12),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (post.media.isNotEmpty()) {
                                val imageUrl = post.media.first() + "?w=800"
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Miniature du lien",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(AppSpecificShapes.ImageShape)
                                )
                                Spacer(modifier = Modifier.width(AppDimensions.spacing12))
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = post.link ?: "Lien externe",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(AppDimensions.spacing4))
                                Text(
                                    text = "Ouvrir le lien",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ExtendedTheme.linkColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                "question" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimensions.spacing12)
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = AppSpecificShapes.CardShape
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimensions.spacing16),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "QUESTION",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.height(AppDimensions.spacing8))
                            Text(
                                text = post.content,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                "tutorial" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimensions.spacing12)
                    ) {
                        if (post.media.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                val imageUrl = post.media.first() + "?w=800"

                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Image du tutoriel",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(AppSpecificShapes.ImageShape)
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(AppDimensions.spacing8)
                                        .background(
                                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                                            shape = AppSpecificShapes.ChipShape
                                        )
                                        .padding(horizontal = AppDimensions.spacing8, vertical = AppDimensions.spacing4)
                                ) {
                                    Text(
                                        text = "TUTORIEL",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                }
                            }
                        }

                        if (post.content.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(AppDimensions.spacing8))
                            Text(
                                text = post.content,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                else -> {
                    if (post.media.isNotEmpty()) {
                        val imageUrl = post.media.first() + "?w=800"
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Media du post",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(AppSpecificShapes.ImageShape)
                        )
                    }
                }
            }
        }

        if (post.tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(AppDimensions.spacing12))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                post.tags.take(3).forEach { tag ->
                    Text(
                        text = "#$tag",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = AppDimensions.spacing8)
                    )
                }

                if (post.tags.size > 3) {
                    Text(
                        text = "+${post.tags.size - 3}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}