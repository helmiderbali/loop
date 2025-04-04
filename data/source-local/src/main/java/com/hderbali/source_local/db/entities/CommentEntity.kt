package com.hderbali.source_local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)