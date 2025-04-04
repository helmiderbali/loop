package com.hderbali.model

data class Notification(
    val id: String,
    val userId: String,
    val type: String,
    val actorId: String?,
    val contentId: String?,
    val commentId: String?,
    val contentPreview: String?,
    val timestamp: String,
    val isRead: Boolean
)
