package com.hderbali.notification

import com.hderbali.model.Notification
import com.hderbali.model.User

data class NotificationsState(
    val isLoading: Boolean = true,
    val notifications: List<NotificationWithUser> = emptyList(),
    val unreadCount: Int = 0,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class NotificationWithUser(
    val notification: Notification,
    val actor: User?
)
