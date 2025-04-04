package com.hderbali.feed.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hderbali.feed.HomeFeedState
import com.hderbali.feed.HomeFeedViewModel
import com.hderbali.feed.views.FeedPostItem
import com.hderbali.feed.views.TrendingPostItem
import com.hderbali.ui.theme.AppDimensions
import com.yourapp.core.ui.theme.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(
    onNavigateToPostDetail: (String) -> Unit,
    onNavigateToUserProfile: (String) -> Unit,
    viewModel: HomeFeedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Loop Social",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::refreshFeed,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading && state.regularPosts.isEmpty() && state.trendingPosts.isEmpty()) {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            } else {
                HomeFeedContent(
                    state = state,
                    onPostClick = onNavigateToPostDetail,
                    onUserClick = onNavigateToUserProfile,
                    onLikeClick = viewModel::likePost
                )
            }
        }
    }
}

@Composable
private fun HomeFeedContent(
    state: HomeFeedState,
    onPostClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onLikeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = AppDimensions.spacing16),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.spacing8)
    ) {
        if (state.trendingPosts.isNotEmpty()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Trending Threads",
                        style = AppTextStyles.SectionHeader,
                        modifier = Modifier.padding(
                            horizontal = AppDimensions.spacing16,
                            vertical = AppDimensions.spacing8
                        )
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = AppDimensions.spacing16),
                        horizontalArrangement = Arrangement.spacedBy(AppDimensions.spacing12)
                    ) {
                        items(state.trendingPosts) { postWithUser ->
                            TrendingPostItem(
                                post = postWithUser.post,
                                user = postWithUser.user,
                                onClick = { onPostClick(postWithUser.post.id) },
                                onUserClick = { postWithUser.user?.let { onUserClick(it.id) } },
                                onLikeClick = { onLikeClick(postWithUser.post.id) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimensions.spacing16))
            }
        }

        if (state.regularPosts.isNotEmpty()) {
            item {
                Text(
                    text = "Recently Shared",
                    style = AppTextStyles.SectionHeader,
                    modifier = Modifier.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )
            }

            items(state.regularPosts) { postWithUser ->
                FeedPostItem(
                    post = postWithUser.post,
                    user = postWithUser.user,
                    onClick = { onPostClick(postWithUser.post.id) },
                    onUserClick = { postWithUser.user?.let { onUserClick(it.id) } },
                    onLikeClick = { onLikeClick(postWithUser.post.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = AppDimensions.spacing16,
                            vertical = AppDimensions.spacing8
                        )
                )
            }
        }

        if (state.regularPosts.isEmpty() && state.trendingPosts.isEmpty() && !state.isLoading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.spacing16),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Aucun post disponible pour le moment.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}