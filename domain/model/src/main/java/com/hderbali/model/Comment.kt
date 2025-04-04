package com.hderbali.model

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val replies: List<Reply>
)
