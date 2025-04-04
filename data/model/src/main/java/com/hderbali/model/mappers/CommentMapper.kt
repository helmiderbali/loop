package com.hderbali.model.mappers

import com.hderbali.model.Comment
import com.hderbali.model.CommentDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun CommentDto.toDomain() = Comment(
    id = id,
    postId = postId,
    userId = userId,
    content = content,
    timestamp = timestamp,
    likeCount = likeCount,
    isLiked = isLiked,
    replies = replies.map { it.toDomain() }
)
