package com.hderbali.notification.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.ui.graphics.vector.ImageVector
import com.hderbali.model.Notification
import com.hderbali.model.User
import com.hderbali.notification.NotificationWithUser
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun groupNotificationsByDate(notifications: List<NotificationWithUser>): Map<String, List<NotificationWithUser>> {
    val now = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val startOfWeek = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    return notifications.groupBy { notificationWithUser ->
        val timestamp = try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(notificationWithUser.notification.timestamp)
            val calendar = Calendar.getInstance().apply { time = date }
            calendar
        } catch (e: Exception) {
            now
        }

        when {
            isSameDay(timestamp, now) -> "Today"
            isSameDay(timestamp, yesterday) -> "Yesterday"
            timestamp.after(startOfWeek) -> "This week"
            else -> "Older"
        }
    }
}

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}


fun formatNotificationTime(timestamp: String): String {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(timestamp)
        val now = Date()

        val diffInMillis = now.time - date.time
        val diffInMinutes = diffInMillis / (60 * 1000)
        val diffInHours = diffInMillis / (60 * 60 * 1000)
        val diffInDays = diffInMillis / (24 * 60 * 60 * 1000)

        return when {
            diffInMinutes < 60 -> {
                if (diffInMinutes < 1) "À l'instant" else "Il y a $diffInMinutes min"
            }
            diffInHours < 24 -> {
                "Il y a $diffInHours h"
            }
            diffInDays < 7 -> {
                "Il y a $diffInDays d"
            }
            else -> {
                val formatter = SimpleDateFormat("d MMM", Locale.getDefault())
                formatter.format(date)
            }
        }
    } catch (e: Exception) {
        return "Récemment"
    }
}

fun handleNotificationClick(
    notification: Notification,
    onNavigateToPostDetail: (String) -> Unit,
    onNavigateToUserProfile: (String) -> Unit
) {
    when (notification.type) {
        "like", "comment", "like_comment", "comment_reply", "mention" -> {
            notification.contentId?.let { postId ->
                onNavigateToPostDetail(postId)
            }
        }
        "follow", "message" -> {
            notification.actorId?.let { userId ->
                onNavigateToUserProfile(userId)
            }
        }
        else -> {
            notification.contentId?.let { contentId ->
                onNavigateToPostDetail(contentId)
            } ?: notification.actorId?.let { actorId ->
                onNavigateToUserProfile(actorId)
            }
        }
    }
}

fun formatNotificationText(notification: Notification, actor: User?): String {
    val actorName = actor?.displayName ?: "Someone"

    return when (notification.type) {
        "like" -> "$actorName liked your post"
        "comment" -> "$actorName commented on your post"
        "follow" -> "$actorName started following you"
        "mention" -> "$actorName mentioned you in a post"
        "like_comment" -> "$actorName liked your comment"
        "comment_reply" -> "$actorName replied to your comment"
        "message" -> "$actorName sent you a message"
        "trending" -> "Your post is trending"
        else -> notification.contentPreview ?: "New notification"
    }
}

fun getNotificationTypeIcon(type: String): ImageVector {
    return when (type) {
        "like" -> Icons.Default.Favorite
        "comment", "comment_reply" -> Icons.Default.ChatBubble
        "follow" -> Icons.Default.Person
        "mention" -> Icons.Default.Person
        "like_comment" -> Icons.Default.Favorite
        "message" -> Icons.Default.ChatBubble
        "trending" -> Icons.Default.Notifications
        "bookmark" -> Icons.Default.Bookmark
        else -> Icons.Default.Photo
    }
}