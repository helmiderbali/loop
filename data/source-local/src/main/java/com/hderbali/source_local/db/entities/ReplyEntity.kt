package com.hderbali.source_local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "replies")
data class ReplyEntity(
    @PrimaryKey val id: String,
    val commentId: String,
    val userId: String,
    val content: String,
    val timestamp: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)
