// com.hderbali.navigation
package com.hderbali.navigation

import SocialScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hderbali.camera.screens.CameraScreen
import com.hderbali.common.NavRoutes
import com.hderbali.discover.screens.DiscoverScreen
import com.hderbali.feed.screens.HomeFeedScreen
import com.hderbali.notification.screens.NotificationsScreen
import com.hderbali.post.screens.PostDetailScreen
import com.hderbali.profile.screens.UserProfileScreen
import com.hderbali.splash.screens.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavRoutes.SPLASH
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore('/')

    val onBottomNavItemSelected: (String) -> Unit = { route ->
        if (currentRoute != route) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }
    }

    val onCameraClick: () -> Unit = {
        navController.navigate(NavRoutes.CAMERA)
    }

    SocialScaffold(
        currentRoute = currentRoute,
        onNavigate = onBottomNavItemSelected,
        onCameraClick = onCameraClick
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavRoutes.SPLASH) {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate(NavRoutes.HOME) {
                            popUpTo(NavRoutes.SPLASH) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.HOME) {
                HomeFeedScreen(
                    onNavigateToPostDetail = { postId ->
                        navController.navigate(NavRoutes.postDetail(postId))
                    },
                    onNavigateToUserProfile = { userId ->
                        navController.navigate(NavRoutes.userProfile(userId))
                    }
                )
            }

            composable(NavRoutes.DISCOVER) {
                DiscoverScreen(
                    onNavigateToSearch = {
                       // TODO
                    },
                    onNavigateToPostDetail = { postId ->
                        navController.navigate(NavRoutes.postDetail(postId))
                    },
                    onNavigateToUserProfile = { userId ->
                        navController.navigate(NavRoutes.userProfile(userId))
                    },
                    onNavigateToTagSearch = { tag ->
                        navController.navigate(NavRoutes.tagSearch(tag))
                    }
                )
            }

            composable(NavRoutes.CAMERA) {
                CameraScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onPostSuccess = {
                        navController.navigate(NavRoutes.HOME) {
                            popUpTo(NavRoutes.HOME) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.NOTIFICATIONS) {
                NotificationsScreen(
                    onNavigateToPostDetail = { postId ->
                        navController.navigate(NavRoutes.postDetail(postId))
                    },
                    onNavigateToUserProfile = { userId ->
                        navController.navigate(NavRoutes.userProfile(userId))
                    }
                )
            }

            composable(NavRoutes.PROFILE) {
                UserProfileScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToSettings = {
                        navController.navigate(NavRoutes.SETTINGS)
                    },
                    onNavigateToEditProfile = {
                        navController.navigate(NavRoutes.EDIT_PROFILE)
                    },
                    onNavigateToPostDetail = { postId ->
                        navController.navigate(NavRoutes.postDetail(postId))
                    }
                )
            }

            composable(NavRoutes.POST_DETAIL) { _ ->
                PostDetailScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToUserProfile = { userId ->
                        navController.navigate(NavRoutes.userProfile(userId))
                    }
                )
            }

            composable(NavRoutes.USER_PROFILE) { _ ->
                UserProfileScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToSettings = {
                        //TODO
                    },
                    onNavigateToEditProfile = {
                        //TODO
                    },
                    onNavigateToPostDetail = { postId ->
                        navController.navigate(NavRoutes.postDetail(postId))
                    }
                )
            }

            composable(NavRoutes.TAG_SEARCH) { _ ->
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                    navController.navigate(NavRoutes.DISCOVER)
                }
            }

            composable(NavRoutes.SETTINGS) {
                //TODO
            }

            // Edit Profile Screen
            composable(NavRoutes.EDIT_PROFILE) {
                //TODO
            }
        }
    }
}