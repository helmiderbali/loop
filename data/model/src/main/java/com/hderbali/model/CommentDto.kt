package com.hderbali.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class CommentDto(
    val id: String,
    val postId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val replies: List<ReplyDto>
)
