package com.hderbali.splash.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.hderbali.splash.SplashViewModel
import com.hderbali.splash.views.SplashScreenContent

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.isDataLoaded) {
        if (state.isDataLoaded) {
            onNavigateToHome()
        }
    }

    SplashScreenContent(
        state = state,
        onRetryClick = viewModel::retryLoading
    )
}