package com.hderbali.profile.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.hderbali.profile.ProfileTab
import com.hderbali.profile.UserProfileState
import com.hderbali.profile.UserProfileViewModel
import com.hderbali.profile.views.PostsGrid
import com.hderbali.profile.views.ProfileHeader
import com.hderbali.profile.views.ProfileStats
import com.hderbali.ui.theme.AppDimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToPostDetail: (String) -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.user?.displayName ?: "Profil",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                actions = {
                    if (state.isCurrentUser) {
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Paramètres"
                            )
                        }
                    } else {
                        IconButton(onClick = { /* Options */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Plus d'options"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.user != null) {
                UserProfileContent(
                    state = state,
                    onTabSelected = viewModel::setSelectedTab,
                    onPostClick = onNavigateToPostDetail,
                    onFollowClick = viewModel::toggleFollow,
                    onEditProfileClick = onNavigateToEditProfile
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserProfileContent(
    state: UserProfileState,
    onTabSelected: (ProfileTab) -> Unit,
    onPostClick: (String) -> Unit,
    onFollowClick: () -> Unit,
    onEditProfileClick: () -> Unit
) {
    val user = state.user ?: return

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileHeader(
            user = user,
            isCurrentUser = state.isCurrentUser,
            isFollowing = state.isFollowing,
            onFollowClick = onFollowClick,
            onEditProfileClick = onEditProfileClick
        )

        Spacer(modifier = Modifier.height(AppDimensions.spacing16))

        ProfileStats(user = user)

        Spacer(modifier = Modifier.height(AppDimensions.spacing16))

        if (user.bio.isNotEmpty()) {
            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = AppDimensions.spacing16)
            )

            Spacer(modifier = Modifier.height(AppDimensions.spacing16))
        }

        val pagerState = rememberPagerState(
            initialPage = state.selectedTab.ordinal,
            pageCount = { ProfileTab.values().size }
        )
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = state.selectedTab.ordinal
        ) {
            ProfileTab.values().forEachIndexed { index, tab ->
                Tab(
                    selected = state.selectedTab == tab,
                    onClick = {
                        onTabSelected(tab)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = when (tab) {
                                ProfileTab.POSTS -> Icons.Default.GridView
                                ProfileTab.LIKED -> Icons.Default.Favorite
                                ProfileTab.BOOKMARKED -> Icons.Default.Bookmark
                            },
                            contentDescription = null
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                ProfileTab.POSTS.ordinal -> PostsGrid(
                    posts = state.userPosts,
                    onPostClick = onPostClick,
                    emptyMessage = "No posts for the moment."
                )
                ProfileTab.LIKED.ordinal -> PostsGrid(
                    posts = state.likedPosts,
                    onPostClick = onPostClick,
                    emptyMessage = "No liked posts yet."
                )
                ProfileTab.BOOKMARKED.ordinal -> PostsGrid(
                    posts = state.bookmarkedPosts,
                    onPostClick = onPostClick,
                    emptyMessage = "No saved posts yet."
                )
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            onTabSelected(ProfileTab.values()[pagerState.currentPage])
        }
    }
}

