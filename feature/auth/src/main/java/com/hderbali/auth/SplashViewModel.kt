package com.hderbali.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.usecase.usecases.post.RefreshPostsUseCase
import com.hderbali.usecase.usecases.user.RefreshUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                refreshUsersUseCase()
                refreshPostsUseCase()

                delay(1500)

                _state.update { it.copy(isLoading = false, isDataLoaded = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement des données."
                    )
                }
            }
        }
    }

    fun retryLoading() {
        _state.update { it.copy(isLoading = true, error = null) }
        loadInitialData()
    }
}