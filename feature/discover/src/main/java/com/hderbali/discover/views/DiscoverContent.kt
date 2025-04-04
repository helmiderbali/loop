package com.hderbali.discover.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hderbali.discover.DiscoverState
import com.hderbali.ui.theme.AppDimensions

@Composable
fun DiscoverContent(
    state: DiscoverState,
    onPostClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onTagClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.Companion.fillMaxSize(),
        contentPadding = PaddingValues(bottom = AppDimensions.spacing16)
    ) {
        if (state.popularTags.isNotEmpty()) {
            item {
                Text(
                    text = "Popular Tags",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppDimensions.spacing16),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.spacing8)
                ) {
                    items(state.popularTags) { tag ->
                        TagChip(
                            tag = tag,
                            onClick = { onTagClick(tag) }
                        )
                    }
                }

                Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing16))
            }
        }

        if (state.suggestedUsers.isNotEmpty()) {
            item {
                Text(
                    text = "Suggested users",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppDimensions.spacing16),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.spacing12)
                ) {
                    items(state.suggestedUsers) { user ->
                        SuggestedUserItem(
                            user = user,
                            onClick = { onUserClick(user.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing16))
            }
        }

        if (state.trendingPosts.isNotEmpty()) {
            item {
                Text(
                    text = "Discover",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = AppDimensions.spacing8),
                    modifier = Modifier.Companion.height(800.dp)
                ) {
                    items(state.trendingPosts) { postWithUser ->
                        DiscoverPostItem(
                            post = postWithUser.post,
                            user = postWithUser.user,
                            onClick = { onPostClick(postWithUser.post.id) },
                            onUserClick = { postWithUser.user?.let { onUserClick(it.id) } }
                        )
                    }
                }
            }
        }
    }
}