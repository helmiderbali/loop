package com.hderbali.profile

import com.hderbali.model.Post
import com.hderbali.model.User

data class UserProfileState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val selectedTab: ProfileTab = ProfileTab.POSTS,
    val userPosts: List<Post> = emptyList(),
    val likedPosts: List<Post> = emptyList(),
    val bookmarkedPosts: List<Post> = emptyList(),
    val isCurrentUser: Boolean = false,
    val isFollowing: Boolean = false,
    val error: String? = null
)

enum class ProfileTab {
    POSTS,
    LIKED,
    BOOKMARKED
}
