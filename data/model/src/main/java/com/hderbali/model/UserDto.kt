package com.hderbali.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class UserDto(
    val id: String,
    val username: String,
    @SerialName("displayName")
    val displayName: String,
    val profilePicUrl: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val isVerified: Boolean,
    val joinDate: String
)
