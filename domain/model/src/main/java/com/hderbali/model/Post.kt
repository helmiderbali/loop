package com.hderbali.model

data class Post(
    val id: String,
    val userId: String,
    val type: String,
    val content: String,
    val media: List<String>,
    val timestamp: String,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val location: String?,
    val tags: List<String>,
    val isTrending: Boolean,
    val isBookmarked: Boolean,
    val isLiked: Boolean,
    val spotifyLink: String? = null,
    val link: String? = null
)
