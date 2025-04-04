package com.hderbali.common

object NavRoutes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val DISCOVER = "discover"
    const val CAMERA = "camera"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"

    const val POST_DETAIL = "post_detail/{postId}"
    const val USER_PROFILE = "user_profile/{userId}"
    const val TAG_SEARCH = "tag_search/{tag}"

    const val SETTINGS = "settings"
    const val EDIT_PROFILE = "edit_profile"

    fun postDetail(postId: String) = "post_detail/$postId"
    fun userProfile(userId: String) = "user_profile/$userId"
    fun tagSearch(tag: String) = "tag_search/$tag"
}
