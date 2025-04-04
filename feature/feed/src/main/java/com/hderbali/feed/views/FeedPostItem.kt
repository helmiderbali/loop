package com.hderbali.feed.views

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.Post
import com.hderbali.model.User
import com.hderbali.ui.common.utils.formatCount
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes
import com.hderbali.ui.theme.ExtendedTheme
import com.yourapp.core.ui.theme.AppTextStyles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun FeedPostItem(
    post: Post,
    user: User?,
    onClick: () -> Unit,
    onUserClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = AppSpecificShapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimensions.cardElevation
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            PostHeader(
                user = user,
                timestamp = post.timestamp,
                onUserClick = onUserClick
            )

            PostContent(post = post)

            PostActions(
                post = post,
                onLikeClick = onLikeClick
            )
        }
    }
}

@Composable
private fun PostContent(post: Post) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (post.content.isNotEmpty()) {
            Text(
                text = post.content,
                style = AppTextStyles.PostContent,
                modifier = Modifier.padding(
                    horizontal = AppDimensions.spacing16,
                    vertical = AppDimensions.spacing8
                )
            )
        }

        if (post.media.isNotEmpty()) {
            when (post.type) {
                "photo", "gallery" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppDimensions.spacing8)
                    ) {
                        val imageUrl = post.media.first() + "?w=800"

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Image du post",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f)
                        )

                        if (post.media.size > 1) {
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(AppDimensions.spacing8),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                            ) {
                                Text(
                                    text = "+${post.media.size - 1}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(
                                        horizontal = AppDimensions.spacing8,
                                        vertical = AppDimensions.spacing4
                                    )
                                )
                            }
                        }
                    }
                }
                "video" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppDimensions.spacing8)
                    ) {
                        val imageUrl = post.media.getOrNull(1) ?: (post.media.first() + "?w=800")

                        AsyncImage(
                            model = post.media.getOrNull(1) ?: post.media.first(), // Utiliser la miniature si disponible
                            contentDescription = "Miniature vidéo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.78f)
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
                            .padding(
                                horizontal = AppDimensions.spacing16,
                                vertical = AppDimensions.spacing8
                            )
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

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Écouter sur Spotify",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = "Nouvelle piste musicale",
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
                            .padding(
                                horizontal = AppDimensions.spacing16,
                                vertical = AppDimensions.spacing8
                            )
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
            }
        }

        if (!post.location.isNullOrEmpty()) {
            Text(
                text = "📍 ${post.location}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(
                    horizontal = AppDimensions.spacing16,
                    vertical = AppDimensions.spacing8
                )
            )
        }
    }
}

@Composable
private fun PostActions(
    post: Post,
    onLikeClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier.padding(top = AppDimensions.spacing8),
            thickness = AppDimensions.dividerThickness,
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimensions.spacing8,
                    vertical = AppDimensions.spacing4
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton(
                icon = if (post.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                text = formatCount(post.likeCount),
                onClick = onLikeClick,
                tint = if (post.isLiked) ExtendedTheme.likeColor else MaterialTheme.colorScheme.onSurface
            )

            ActionButton(
                icon = Icons.Filled.ChatBubbleOutline,
                text = formatCount(post.commentCount),
                onClick = { /* Naviguer vers les commentaires */ }
            )

            ActionButton(
                icon = if (post.isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                text = "",
                onClick = { /* Enregistrer le post */ },
                tint = if (post.isBookmarked) ExtendedTheme.bookmarkColor else MaterialTheme.colorScheme.onSurface
            )

            ActionButton(
                icon = Icons.Filled.Share,
                text = "",
                onClick = { /* Partager le post */ }
            )
        }
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = AppDimensions.spacing8, vertical = AppDimensions.spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )

        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(AppDimensions.spacing4))
            Text(
                text = text,
                style = AppTextStyles.Count,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TrendingPostItem(
    post: Post,
    user: User?,
    onClick: () -> Unit,
    onUserClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(250.dp)
            .clickable(onClick = onClick),
        shape = AppSpecificShapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimensions.cardElevation
        )
    ) {
        Column {
            PostHeader(
                user = user,
                timestamp = post.timestamp,
                onUserClick = onUserClick,
                isTrending = true
            )

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    horizontal = AppDimensions.spacing12,
                    vertical = AppDimensions.spacing8
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimensions.spacing12,
                        vertical = AppDimensions.spacing8
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.isLiked) ExtendedTheme.likeColor else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable(onClick = onLikeClick)
                    )
                    Spacer(modifier = Modifier.width(AppDimensions.spacing4))
                    Text(
                        text = formatCount(post.likeCount),
                        style = AppTextStyles.Count,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(AppDimensions.spacing4))
                    Text(
                        text = formatCount(post.commentCount),
                        style = AppTextStyles.Count,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun PostHeader(
    user: User?,
    timestamp: String,
    onUserClick: () -> Unit,
    isTrending: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimensions.spacing12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(onClick = onUserClick),
            contentAlignment = Alignment.Center
        ) {
            if (user?.profilePicUrl?.isNotEmpty() == true) {
                val imageUrl = user.profilePicUrl + "?w=800"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photo de profil de ${user.displayName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.width(AppDimensions.spacing12))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user?.displayName ?: "User",
                    style = AppTextStyles.Username,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (user?.isVerified == true) {
                    Spacer(modifier = Modifier.width(AppDimensions.spacing4))
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = "Compte vérifié",
                        tint = ExtendedTheme.verifiedColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = formatTimestamp(timestamp),
                style = AppTextStyles.Timestamp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isTrending) {
            Icon(
                painter = painterResource(id = R.drawable.btn_star_big_on),
                contentDescription = "Trending",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(24.dp)
            )
        } else {
            IconButton(onClick = { /* Menu options */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun formatTimestamp(timestamp: String): String {
    return remember(timestamp) {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = format.parse(timestamp) ?: return@remember "à l'instant"
            val now = Date()
            val diff = now.time - date.time

            when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "à l'instant"
                diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} min"
                diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} h"
                diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)} d"
                else -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(date)
            }
        } catch (e: Exception) {
            "à l'instant"
        }
    }
}
