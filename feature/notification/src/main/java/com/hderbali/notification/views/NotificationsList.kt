package com.hderbali.notification.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hderbali.notification.NotificationWithUser
import com.hderbali.notification.screens.groupNotificationsByDate
import com.hderbali.ui.theme.AppDimensions

@Composable
fun NotificationsList(
    notifications: List<NotificationWithUser>,
    onNotificationClick: (NotificationWithUser) -> Unit
) {
    LazyColumn(
        modifier = Modifier.Companion.fillMaxSize(),
        contentPadding = PaddingValues(vertical = AppDimensions.spacing8)
    ) {
        val groupedNotifications = groupNotificationsByDate(notifications)

        groupedNotifications.forEach { (dateGroup, notificationsInGroup) ->
            item {
                Text(
                    text = dateGroup,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )
            }

            items(notificationsInGroup) { notificationWithUser ->
                NotificationItem(
                    notificationWithUser = notificationWithUser,
                    onClick = { onNotificationClick(notificationWithUser) }
                )

                Divider(
                    modifier = Modifier.Companion.padding(
                        start = 72.dp,
                        end = AppDimensions.spacing16
                    ),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}