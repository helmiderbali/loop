package com.hderbali.discover.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hderbali.discover.SearchResult
import com.hderbali.discover.SearchResultType
import com.hderbali.ui.theme.AppDimensions

@Composable
fun SearchResults(
    results: List<SearchResult>,
    onPostClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onTagClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.Companion.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = AppDimensions.spacing8)
    ) {
        val userResults = results.filter { it.type == SearchResultType.USER }
        val postResults = results.filter { it.type == SearchResultType.POST }
        val tagResults = results.filter { it.type == SearchResultType.TAG }

        if (userResults.isNotEmpty()) {
            item {
                Text(
                    text = "Utilisateurs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )
            }

            items(userResults) { result ->
                result.user?.let { user ->
                    UserSearchItem(
                        user = user,
                        onClick = { onUserClick(user.id) }
                    )
                }
            }

            item { Divider(modifier = Modifier.Companion.padding(vertical = AppDimensions.spacing8)) }
        }

        if (tagResults.isNotEmpty()) {
            item {
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppDimensions.spacing16),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.spacing8)
                ) {
                    items(tagResults) { result ->
                        result.tag?.let { tag ->
                            TagChip(
                                tag = tag,
                                onClick = { onTagClick(tag) }
                            )
                        }
                    }
                }
            }

            item { Divider(modifier = Modifier.Companion.padding(vertical = AppDimensions.spacing8)) }
        }

        if (postResults.isNotEmpty()) {
            item {
                Text(
                    text = "Posts",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.Companion.padding(
                        horizontal = AppDimensions.spacing16,
                        vertical = AppDimensions.spacing8
                    )
                )
            }

            items(postResults) { result ->
                result.post?.let { post ->
                    PostSearchItem(
                        post = post,
                        onClick = { onPostClick(post.id) }
                    )
                }
            }
        }
    }
}