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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.hderbali.model.User
import com.hderbali.ui.common.utils.formatTimestamp
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme
import com.yourapp.core.ui.theme.AppTextStyles

@Composable
fun PostHeader(
    user: User?,
    timestamp: String,
    location: String?,
    onUserClick: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(AppDimensions.spacing16)
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Box(
                modifier = Modifier.Companion
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onUserClick),
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
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing16))

            Column(modifier = Modifier.Companion.weight(1f)) {
                Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                    Text(
                        text = user?.displayName ?: "User",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Companion.Ellipsis
                    )

                    if (user?.isVerified == true) {
                        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
                        Icon(
                            imageVector = Icons.Outlined.Verified,
                            contentDescription = "Compte vérifié",
                            tint = ExtendedTheme.verifiedColor,
                            modifier = Modifier.Companion.size(16.dp)
                        )
                    }
                }

                Text(
                    text = formatTimestamp(timestamp),
                    style = AppTextStyles.Timestamp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!location.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing4))
                    Text(
                        text = "📍 $location",
                        style = AppTextStyles.Timestamp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

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