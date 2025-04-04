package com.hderbali.feed

import com.hderbali.model.Post
import com.hderbali.model.User

data class HomeFeedState(
    val isLoading: Boolean = false,
    val trendingPosts: List<PostWithUser> = emptyList(),
    val regularPosts: List<PostWithUser> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)

data class PostWithUser(
    val post: Post,
    val user: User?
)