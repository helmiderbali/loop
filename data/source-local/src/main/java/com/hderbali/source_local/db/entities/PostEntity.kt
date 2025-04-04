package com.hderbali.source_local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
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
    val spotifyLink: String?,
    val link: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)
