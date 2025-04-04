package com.hderbali.post.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hderbali.model.Post
import com.hderbali.ui.common.utils.formatCount
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme

@Composable
fun PostActions(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(
            icon = if (post.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            text = formatCount(post.likeCount),
            onClick = onLikeClick,
            tint = if (post.isLiked) ExtendedTheme.likeColor else MaterialTheme.colorScheme.onSurface
        )

        ActionButton(
            icon = if (post.commentCount > 0) Icons.Filled.ChatBubble else Icons.Filled.ChatBubbleOutline,
            text = formatCount(post.commentCount),
            onClick = onCommentClick,
            tint = if (post.commentCount > 0) ExtendedTheme.commentColor else MaterialTheme.colorScheme.onSurface
        )

        ActionButton(
            icon = if (post.isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            text = "",
            onClick = onBookmarkClick,
            tint = if (post.isBookmarked) ExtendedTheme.bookmarkColor else MaterialTheme.colorScheme.onSurface
        )

        ActionButton(
            icon = Icons.Filled.Share,
            text = "",
            onClick = onShareClick
        )
    }
}