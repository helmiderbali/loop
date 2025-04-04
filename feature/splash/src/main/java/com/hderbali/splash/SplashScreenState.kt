package com.hderbali.splash

data class SplashScreenState(
    val isLoading: Boolean = true,
    val isDataLoaded: Boolean = false,
    val error: String? = null
)
