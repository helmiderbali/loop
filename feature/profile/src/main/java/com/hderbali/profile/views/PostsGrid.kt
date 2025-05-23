package com.hderbali.profile.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hderbali.model.Post
import com.hderbali.ui.theme.AppDimensions

@Composable
fun PostsGrid(
    posts: List<Post>,
    onPostClick: (String) -> Unit,
    emptyMessage: String
) {
    if (posts.isEmpty()) {
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(AppDimensions.spacing16),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text(
                text = emptyMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Companion.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier.Companion.fillMaxSize()
        ) {
            items(posts) { post ->
                PostGridItem(
                    post = post,
                    onClick = { onPostClick(post.id) }
                )
            }
        }
    }
}