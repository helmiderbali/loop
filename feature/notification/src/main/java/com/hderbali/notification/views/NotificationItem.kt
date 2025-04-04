package com.hderbali.notification.views

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.notification.NotificationWithUser
import com.hderbali.notification.screens.formatNotificationText
import com.hderbali.notification.screens.formatNotificationTime
import com.hderbali.notification.screens.getNotificationTypeColor
import com.hderbali.notification.screens.getNotificationTypeIcon
import com.hderbali.ui.theme.AppDimensions

@Composable
fun NotificationItem(
    notificationWithUser: NotificationWithUser,
    onClick: () -> Unit
) {
    val notification = notificationWithUser.notification
    val actor = notificationWithUser.actor

    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                if (!notification.isRead)
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                else
                    Color.Companion.Transparent
            )
            .padding(
                horizontal = AppDimensions.spacing16,
                vertical = AppDimensions.spacing12
            ),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Box(
            modifier = Modifier.Companion
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    if (actor != null) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        getNotificationTypeColor(notification.type)
                    }
                ),
            contentAlignment = Alignment.Companion.Center
        ) {
            if (actor != null && actor.profilePicUrl.isNotEmpty()) {
                val imageUrl = actor.profilePicUrl + "?w=800"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photo de profil de ${actor.displayName}",
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = getNotificationTypeIcon(notification.type),
                    contentDescription = null,
                    tint = if (actor != null) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.background
                    },
                    modifier = Modifier.Companion.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing16))

        Column(modifier = Modifier.Companion.weight(1f)) {
            Text(
                text = formatNotificationText(notification, actor),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (!notification.isRead) FontWeight.Companion.SemiBold else FontWeight.Companion.Normal,
                maxLines = 3,
                overflow = TextOverflow.Companion.Ellipsis
            )

            Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing4))

            Text(
                text = formatNotificationTime(notification.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (!notification.isRead) {
            Box(
                modifier = Modifier.Companion
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}