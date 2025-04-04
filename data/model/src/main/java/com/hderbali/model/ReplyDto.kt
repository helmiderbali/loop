package com.hderbali.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class ReplyDto(
    val id: String,
    val commentId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean
)
