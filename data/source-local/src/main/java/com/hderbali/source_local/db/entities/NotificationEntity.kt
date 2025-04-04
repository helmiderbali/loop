package com.hderbali.source_local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val type: String,
    val actorId: String?,
    val contentId: String?,
    val commentId: String?,
    val contentPreview: String?,
    val timestamp: String,
    val isRead: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)
