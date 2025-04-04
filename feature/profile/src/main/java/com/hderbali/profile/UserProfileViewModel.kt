package com.hderbali.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.post.GetPostsByUserIdUseCase
import com.hderbali.ui.usescases.profile.GetCurrentUserUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    private val userId: String? = savedStateHandle["userId"]
    private val isCurrentUserProfile = userId == null

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                if (isCurrentUserProfile) {
                    loadCurrentUserProfile()
                } else {
                    userId?.let {
                        checkIfCurrentUser(it)
                        loadUserDetails(it)
                        loadUserPosts(it)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement du profil."
                    )
                }
            }
        }
    }

    private suspend fun loadCurrentUserProfile() {
        getCurrentUserUseCase()
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement de l'utilisateur actuel."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val currentUser = result.data
                        _state.update {
                            it.copy(
                                user = currentUser,
                                isCurrentUser = true,
                                isLoading = false
                            )
                        }
                        loadUserPosts(currentUser.id)
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


    private suspend fun checkIfCurrentUser(userId: String) {
        getCurrentUserUseCase()
            .catch { e ->
            }
            .collectLatest { result ->
                if (result is ResultOf.Success) {
                    val currentUser = result.data
                    val isCurrentUser = currentUser.id == userId
                    _state.update { it.copy(isCurrentUser = isCurrentUser) }
                }
            }
    }

    private suspend fun loadUserDetails(userId: String) {
        getUserByIdUseCase(userId)
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement des détails de l'utilisateur."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        _state.update {
                            it.copy(
                                user = result.data,
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

    private suspend fun loadUserPosts(userId: String) {
        getPostsByUserIdUseCase(userId)
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
                        val posts = result.data
                        val likedPosts = posts.filter { it.isLiked }
                        val bookmarkedPosts = posts.filter { it.isBookmarked }

                        _state.update {
                            it.copy(
                                userPosts = posts,
                                likedPosts = likedPosts,
                                bookmarkedPosts = bookmarkedPosts,
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

    fun setSelectedTab(tab: ProfileTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun toggleFollow() {
        _state.update { it.copy(isFollowing = !it.isFollowing) }
    }

    fun refreshProfile() {
        loadProfile()
    }
}
