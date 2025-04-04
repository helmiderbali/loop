package com.hderbali.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hderbali.auth.SplashScreen
import com.hderbali.auth.SplashViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    splashViewModel: SplashViewModel,
    startDestination: String = NavRoutes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                viewModel = splashViewModel,
                onNavigateToHome = {
                    // Naviguer vers l'écran d'accueil et effacer le backstack
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen (sera implémenté plus tard)
        composable(NavRoutes.HOME) {
            // Placeholder pour l'écran d'accueil
            // HomeScreen()
        }

        // Autres écrans seront ajoutés au fur et à mesure
    }
}
