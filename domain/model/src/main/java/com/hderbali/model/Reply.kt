package com.hderbali.model

data class Reply(
    val id: String,
    val commentId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean
)
