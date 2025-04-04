package com.hderbali.model.mappers

import com.hderbali.model.Post
import com.hderbali.model.PostDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun PostDto.toDomain() = Post(
    id = id,
    userId = userId,
    type = type,
    content = content,
    media = media,
    timestamp = timestamp,
    likeCount = likeCount,
    commentCount = commentCount,
    viewCount = viewCount,
    location = location,
    tags = tags,
    isTrending = isTrending,
    isBookmarked = isBookmarked,
    isLiked = isLiked,
    spotifyLink = spotifyLink,
    link = link
)
