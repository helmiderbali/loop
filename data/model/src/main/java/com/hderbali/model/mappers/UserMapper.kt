package com.hderbali.model.mappers

import com.hderbali.model.User
import com.hderbali.model.UserDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun UserDto.toDomain() = User(
    id = id,
    username = username,
    displayName = displayName,
    profilePicUrl = profilePicUrl,
    bio = bio,
    followerCount = followerCount,
    followingCount = followingCount,
    postCount = postCount,
    isVerified = isVerified,
    joinDate = joinDate
)
