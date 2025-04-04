package com.hderbali.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.post.GetPostsUseCase
import com.hderbali.ui.usescases.post.GetTrendingPostsUseCase
import com.hderbali.ui.usescases.post.LikePostUseCase
import com.hderbali.ui.usescases.post.RefreshPostsUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getTrendingPostsUseCase: GetTrendingPostsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeFeedState())
    val state: StateFlow<HomeFeedState> = _state.asStateFlow()

    private val userCache = mutableMapOf<String, User>()

    init {
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                launch { loadRegularPosts() }
                launch { loadTrendingPosts() }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement du feed."
                    )
                }
            }
        }
    }

    fun refreshFeed() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, error = null) }

            try {
                refreshPostsUseCase()
                loadRegularPosts()
                loadTrendingPosts()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "Une erreur s'est produite lors du rafraîchissement."
                    )
                }
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            try {
                likePostUseCase(postId).collectLatest { result ->
                    if (result is ResultOf.Success && result.data) {
                        updatePostLikeState(postId)
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Impossible de liker ce post.") }
            }
        }
    }

    private suspend fun loadRegularPosts() {
        getPostsUseCase()
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e.message ?: "Erreur lors du chargement des posts."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val posts = result.data
                        val postsWithUsers = posts.map { post ->
                            PostWithUser(post, getUserFromCache(post.userId))
                        }
                        _state.update {
                            it.copy(
                                regularPosts = postsWithUsers,
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                    is ResultOf.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.exception.message ?: "Une erreur s'est produite."
                            )
                        }
                    }
                    ResultOf.Loading -> {
                    }
                }
            }
    }

    private suspend fun loadTrendingPosts() {
        getTrendingPostsUseCase()
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e.message ?: "Erreur lors du chargement des posts tendance."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val trendingPosts = result.data
                        val postsWithUsers = trendingPosts.map { post ->
                            PostWithUser(post, getUserFromCache(post.userId))
                        }
                        _state.update {
                            it.copy(
                                trendingPosts = postsWithUsers,
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                    is ResultOf.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.exception.message ?: "Une erreur s'est produite."
                            )
                        }
                    }
                    ResultOf.Loading -> {
                    }
                }
            }
    }

    private suspend fun getUserFromCache(userId: String): User? {
        userCache[userId]?.let { return it }

        return try {
            val result = getUserByIdUseCase(userId)
                .first { it is ResultOf.Success || it is ResultOf.Error }
            if (result is ResultOf.Success) {
                val user = result.data
                userCache[userId] = user
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun updatePostLikeState(postId: String) {
        _state.update { currentState ->
            val updatedRegularPosts = currentState.regularPosts.map { postWithUser ->
                if (postWithUser.post.id == postId) {
                    val updatedPost = postWithUser.post.copy(
                        isLiked = !postWithUser.post.isLiked,
                        likeCount = if (postWithUser.post.isLiked) {
                            postWithUser.post.likeCount - 1
                        } else {
                            postWithUser.post.likeCount + 1
                        }
                    )
                    postWithUser.copy(post = updatedPost)
                } else {
                    postWithUser
                }
            }

            val updatedTrendingPosts = currentState.trendingPosts.map { postWithUser ->
                if (postWithUser.post.id == postId) {
                    val updatedPost = postWithUser.post.copy(
                        isLiked = !postWithUser.post.isLiked,
                        likeCount = if (postWithUser.post.isLiked) {
                            postWithUser.post.likeCount - 1
                        } else {
                            postWithUser.post.likeCount + 1
                        }
                    )
                    postWithUser.copy(post = updatedPost)
                } else {
                    postWithUser
                }
            }

            currentState.copy(
                regularPosts = updatedRegularPosts,
                trendingPosts = updatedTrendingPosts
            )
        }
    }
}