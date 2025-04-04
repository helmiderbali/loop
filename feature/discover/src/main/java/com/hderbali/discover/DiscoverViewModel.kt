package com.hderbali.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.model.User
import com.hderbali.ui.usescases.post.GetPostsUseCase
import com.hderbali.ui.usescases.post.GetTrendingPostsUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import com.hderbali.ui.usescases.profile.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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
class DiscoverViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getTrendingPostsUseCase: GetTrendingPostsUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverState())
    val state: StateFlow<DiscoverState> = _state.asStateFlow()

    private val userCache = mutableMapOf<String, User>()
    private val allPosts = mutableListOf<Post>()

    init {
        loadDiscoverContent()
    }

    private fun loadDiscoverContent() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                launch { loadTrendingPosts() }
                launch { loadSuggestedUsers() }
                launch { extractPopularTags() }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement du contenu."
                    )
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
                        error = e.message ?: "Erreur lors du chargement des posts en tendance."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val trendingPosts = result.data
                        allPosts.addAll(trendingPosts)

                        val postsWithUsers = trendingPosts.map { post ->
                            PostWithUser(post, getUserFromCache(post.userId))
                        }

                        _state.update {
                            it.copy(
                                trendingPosts = postsWithUsers,
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

    private suspend fun loadSuggestedUsers() {
        getUsersUseCase()
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement des utilisateurs suggérés."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val allUsers = result.data

                        val suggestedUsers = allUsers
                            .filter { it.isVerified || it.followerCount > 1000 }
                            .take(10)

                        allUsers.forEach { user ->
                            userCache[user.id] = user
                        }

                        _state.update {
                            it.copy(
                                suggestedUsers = suggestedUsers,
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

    private suspend fun extractPopularTags() {
        getPostsUseCase()
            .catch { e ->
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val posts = result.data
                        allPosts.addAll(posts)

                        val tagCounts = mutableMapOf<String, Int>()
                        posts.forEach { post ->
                            post.tags.forEach { tag ->
                                tagCounts[tag] = (tagCounts[tag] ?: 0) + 1
                            }
                        }

                        val popularTags = tagCounts.entries
                            .sortedByDescending { it.value }
                            .map { it.key }
                            .take(15)

                        _state.update {
                            it.copy(
                                popularTags = popularTags,
                                isLoading = false
                            )
                        }
                    }
                    else -> {
                    }
                }
            }
    }

    @OptIn(FlowPreview::class)
    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query, isSearching = query.isNotEmpty()) }

        if (query.isBlank()) {
            _state.update { it.copy(searchResults = emptyList()) }
            return
        }

        viewModelScope.launch {
            // Ajouter un debounce pour éviter les recherches trop fréquentes
            delay(300)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            val normalizedQuery = query.trim().lowercase()
            val results = mutableListOf<SearchResult>()

            // Rechercher dans les posts
            val matchingPosts = allPosts.filter { post ->
                post.content.lowercase().contains(normalizedQuery) ||
                        post.tags.any { it.lowercase().contains(normalizedQuery) }
            }.take(5)

            results.addAll(matchingPosts.map { post ->
                SearchResult(type = SearchResultType.POST, post = post)
            })

            // Rechercher dans les utilisateurs
            val matchingUsers = userCache.values.filter { user ->
                user.username.lowercase().contains(normalizedQuery) ||
                        user.displayName.lowercase().contains(normalizedQuery)
            }.take(5)

            results.addAll(matchingUsers.map { user ->
                SearchResult(type = SearchResultType.USER, user = user)
            })

            // Rechercher dans les tags
            val matchingTags = _state.value.popularTags.filter { tag ->
                tag.lowercase().contains(normalizedQuery)
            }.take(5)

            results.addAll(matchingTags.map { tag ->
                SearchResult(type = SearchResultType.TAG, tag = tag)
            })

            _state.update { it.copy(searchResults = results) }
        }
    }

    fun clearSearch() {
        _state.update { it.copy(searchQuery = "", searchResults = emptyList(), isSearching = false) }
    }

    fun selectCategory(category: DiscoverCategory) {
        _state.update { it.copy(selectedCategory = category) }

        viewModelScope.launch {
            // Filtrer les posts basés sur la catégorie sélectionnée
            when (category) {
                DiscoverCategory.ALL -> {
                    // Pas besoin de filtrer, juste recharger tout
                    loadTrendingPosts()
                }
                DiscoverCategory.PHOTOS -> {
                    filterPostsByType("photo", "gallery")
                }
                DiscoverCategory.VIDEOS -> {
                    filterPostsByType("video")
                }
                DiscoverCategory.MUSIC -> {
                    filterPostsByType("music")
                }
                DiscoverCategory.TRENDING -> {
                    // Déjà chargé par loadTrendingPosts()
                    loadTrendingPosts()
                }
            }
        }
    }

    private suspend fun filterPostsByType(vararg types: String) {
        getPostsUseCase()
            .catch { e ->
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val posts = result.data
                        val filteredPosts = posts.filter { post ->
                            types.contains(post.type)
                        }

                        val postsWithUsers = filteredPosts.map { post ->
                            PostWithUser(post, getUserFromCache(post.userId))
                        }

                        _state.update {
                            it.copy(
                                trendingPosts = postsWithUsers,
                                isLoading = false
                            )
                        }
                    }
                    else -> {
                        // Ignorer les autres états
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

    fun refreshContent() {
        loadDiscoverContent()
    }
}