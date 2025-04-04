package com.hderbali.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
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