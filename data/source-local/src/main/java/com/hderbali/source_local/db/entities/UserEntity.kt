package com.hderbali.source_local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val displayName: String,
    val profilePicUrl: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val isVerified: Boolean,
    val joinDate: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
