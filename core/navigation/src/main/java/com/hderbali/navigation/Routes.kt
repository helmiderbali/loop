package com.hderbali.navigation

object NavRoutes {
    // Écrans principaux
    const val SPLASH = "splash"
    const val HOME = "home"
    const val DISCOVER = "discover"
    const val CAMERA = "camera"
    const val PROFILE = "profile"
    const val NOTIFICATIONS = "notifications"
    const val MESSAGES = "messages"

    // Écrans avec paramètres
    const val POST_DETAIL = "post_detail/{postId}"
    const val USER_PROFILE = "user_profile/{userId}"

    // Fonctions pour construire les routes avec paramètres
    fun postDetail(postId: String) = "post_detail/$postId"
    fun userProfile(userId: String) = "user_profile/$userId"
}

