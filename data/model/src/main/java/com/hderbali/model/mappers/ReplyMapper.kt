package com.hderbali.model.mappers

import com.hderbali.model.Reply
import com.hderbali.model.ReplyDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun ReplyDto.toDomain() = Reply(
    id = id,
    commentId = commentId,
    userId = userId,
    content = content,
    timestamp = timestamp,
    likeCount = likeCount,
    isLiked = isLiked
)
