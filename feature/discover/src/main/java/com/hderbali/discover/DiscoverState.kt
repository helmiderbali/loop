package com.hderbali.discover

import com.hderbali.model.Post
import com.hderbali.model.User

data class DiscoverState(
    val isLoading: Boolean = true,
    val trendingPosts: List<PostWithUser> = emptyList(),
    val suggestedUsers: List<User> = emptyList(),
    val popularTags: List<String> = emptyList(),
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val isSearching: Boolean = false,
    val selectedCategory: DiscoverCategory = DiscoverCategory.ALL,
    val error: String? = null
)

data class PostWithUser(
    val post: Post,
    val user: User?
)

data class SearchResult(
    val type: SearchResultType,
    val post: Post? = null,
    val user: User? = null,
    val tag: String? = null
)

enum class SearchResultType {
    POST, USER, TAG
}

enum class DiscoverCategory {
    ALL, PHOTOS, VIDEOS, MUSIC, TRENDING
}
