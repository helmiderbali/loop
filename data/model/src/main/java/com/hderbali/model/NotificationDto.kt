package com.hderbali.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class NotificationDto(
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
