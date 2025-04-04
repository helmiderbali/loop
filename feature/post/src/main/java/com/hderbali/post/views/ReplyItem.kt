package com.hderbali.post.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.post.ReplyWithUser
import com.hderbali.ui.common.utils.formatCount
import com.hderbali.ui.common.utils.formatTimestamp
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme
import com.yourapp.core.ui.theme.AppTextStyles

@Composable
fun ReplyItem(
    replyWithUser: ReplyWithUser,
    onUserClick: (String) -> Unit,
    onLikeReply: (String) -> Unit
) {
    val reply = replyWithUser.reply
    val user = replyWithUser.user

    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(
                start = 48.dp,
                top = AppDimensions.spacing8,
                end = AppDimensions.spacing16
            ),
        verticalAlignment = Alignment.Companion.Top
    ) {
        Box(
            modifier = Modifier.Companion
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { user?.id?.let { onUserClick(it) } },
            contentAlignment = Alignment.Companion.Center
        ) {
            if (user?.profilePicUrl?.isNotEmpty() == true) {
                val imageUrl = user.profilePicUrl + "?w=800"

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photo de profil de ${user.displayName}",
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.Companion.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing8))

        Column(modifier = Modifier.Companion.weight(1f)) {
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = user?.displayName ?: "User",
                    style = AppTextStyles.Username,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = AppTextStyles.Username.fontSize.times(0.9f)
                )

                if (user?.isVerified == true) {
                    Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing2))
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = "Compte vérifié",
                        tint = ExtendedTheme.verifiedColor,
                        modifier = Modifier.Companion.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing2))

            Text(
                text = reply.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing2))

            Row(
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = formatTimestamp(reply.timestamp),
                    style = AppTextStyles.Timestamp,
                    fontSize = AppTextStyles.Timestamp.fontSize.times(0.9f),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing8))

                // Likes
                Row(
                    modifier = Modifier.Companion.clickable { onLikeReply(reply.id) },
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Icon(
                        imageVector = if (reply.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (reply.isLiked) ExtendedTheme.likeColor else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.Companion.size(14.dp)
                    )

                    if (reply.likeCount > 0) {
                        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing2))
                        Text(
                            text = formatCount(reply.likeCount),
                            style = AppTextStyles.Timestamp,
                            fontSize = AppTextStyles.Timestamp.fontSize.times(0.9f),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
