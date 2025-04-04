package com.hderbali.model

data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val profilePicUrl: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val isVerified: Boolean,
    val joinDate: String
)
