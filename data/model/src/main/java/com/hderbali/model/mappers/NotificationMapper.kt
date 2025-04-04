package com.hderbali.model.mappers

import com.hderbali.model.Notification
import com.hderbali.model.NotificationDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun NotificationDto.toDomain() = Notification(
    id = id,
    userId = userId,
    type = type,
    actorId = actorId,
    contentId = contentId,
    commentId = commentId,
    contentPreview = contentPreview,
    timestamp = timestamp,
    isRead = isRead
)
