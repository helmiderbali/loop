package com.hderbali.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.post.GetPostsUseCase
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
import kotlin.collections.filter

data class TagSearchState(
    val isLoading: Boolean = false,
    val posts: List<PostWithUser> = emptyList(),
    val error: String? = null,
    val currentTag: String? = null
)

@HiltViewModel
class TagSearchViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TagSearchState())
    val state: StateFlow<TagSearchState> = _state.asStateFlow()

    private val userCache = mutableMapOf<String, User>()

    fun searchByTag(tag: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, currentTag = tag) }

            try {
                loadPostsByTag(tag)
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors de la recherche."
                    )
                }
            }
        }
    }

    private suspend fun loadPostsByTag(tag: String) {
        getPostsUseCase()
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement des posts."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val allPosts = result.data

                        val filteredPosts = allPosts.filter { post ->
                            post.tags.any { it.equals(tag, ignoreCase = true) }
                        }

                        val postsWithUsers = filteredPosts.map { post ->
                            PostWithUser(post, getUserFromCache(post.userId))
                        }

                        _state.update {
                            it.copy(
                                posts = postsWithUsers,
                                isLoading = false
                            )
                        }
                    }
                    is ResultOf.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
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
}