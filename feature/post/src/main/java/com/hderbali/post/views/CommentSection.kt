package com.hderbali.post.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hderbali.ui.theme.AppDimensions

@Composable
fun CommentSection(
    isExpanded: Boolean,
    commentCount: Int,
    onToggleExpand: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16)
            .clickable(onClick = onToggleExpand)
    ) {
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(vertical = AppDimensions.spacing8),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text = if (commentCount > 0) {
                    "Voir les $commentCount commentaires"
                } else {
                    "Soyez le premier à commenter"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                imageVector = if (isExpanded) Icons.Filled.ChatBubble else Icons.Filled.ChatBubbleOutline,
                contentDescription = if (isExpanded) "Masquer les commentaires" else "Afficher les commentaires",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}