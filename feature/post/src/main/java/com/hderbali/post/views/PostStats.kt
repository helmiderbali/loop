package com.hderbali.post.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hderbali.model.Post
import com.hderbali.ui.common.utils.formatCount
import com.hderbali.ui.theme.AppDimensions

@Composable
fun PostStats(post: Post) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8)
    ) {
        Text(
            text = "${formatCount(post.likeCount)} likes · ${formatCount(post.commentCount)} commentaires · ${
                formatCount(
                    post.viewCount
                )
            } views",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider(
            modifier = Modifier.Companion.padding(vertical = AppDimensions.spacing8),
            thickness = AppDimensions.dividerThickness,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}