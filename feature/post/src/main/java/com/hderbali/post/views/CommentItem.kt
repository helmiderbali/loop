package com.hderbali.post.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.post.CommentWithUser
import com.hderbali.ui.common.utils.formatCount
import com.hderbali.ui.common.utils.formatTimestamp
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes
import com.hderbali.ui.theme.ExtendedTheme
import com.yourapp.core.ui.theme.AppTextStyles

@Composable
fun CommentItem(
    commentWithUser: CommentWithUser,
    onUserClick: (String) -> Unit,
    onLikeComment: (String) -> Unit,
    onReplyClick: (String) -> Unit,
    onReplyTextChanged: (String, String) -> Unit,
    onSubmitReply: (String) -> Unit
) {
    val comment = commentWithUser.comment
    val user = commentWithUser.user

    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(
                horizontal = AppDimensions.spacing16,
                vertical = AppDimensions.spacing8
            )
    ) {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            verticalAlignment = Alignment.Companion.Top
        ) {
            Box(
                modifier = Modifier.Companion
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { user?.id?.let { onUserClick(it) } },
                contentAlignment = Alignment.Companion.Center
            ) {
                if (user?.profilePicUrl?.isNotEmpty() == true) {
                    val imageUrl = user.profilePicUrl + "?w=800"
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Photo de profil de ${user.displayName}",
                        contentScale = ContentScale.Companion.Crop,
                        modifier = Modifier.Companion.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing12))

            Column(modifier = Modifier.Companion.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Text(
                        text = user?.displayName ?: "User",
                        style = AppTextStyles.Username,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    if (user?.isVerified == true) {
                        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
                        Icon(
                            imageVector = Icons.Outlined.Verified,
                            contentDescription = "Compte vérifié",
                            tint = ExtendedTheme.verifiedColor,
                            modifier = Modifier.Companion.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing4))

                Text(
                    text = comment.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing4))

                Row(
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Text(
                        text = formatTimestamp(comment.timestamp),
                        style = AppTextStyles.Timestamp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing12))

                    Row(
                        modifier = Modifier.Companion.clickable { onLikeComment(comment.id) },
                        verticalAlignment = Alignment.Companion.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (comment.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (comment.isLiked) ExtendedTheme.likeColor else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.Companion.size(16.dp)
                        )

                        if (comment.likeCount > 0) {
                            Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
                            Text(
                                text = formatCount(comment.likeCount),
                                style = AppTextStyles.Timestamp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing12))

                    Text(
                        text = "Répondre",
                        style = AppTextStyles.Timestamp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.Companion.clickable { onReplyClick(comment.id) }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = commentWithUser.isReplying,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(start = 48.dp, top = AppDimensions.spacing8),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentWithUser.replyText,
                    onValueChange = { onReplyTextChanged(comment.id, it) },
                    modifier = Modifier.Companion.weight(1f),
                    placeholder = { Text("Répondre à ${user?.displayName ?: "ce commentaire"}...") },
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Companion.Send),
                    keyboardActions = KeyboardActions(onSend = { onSubmitReply(comment.id) }),
                    trailingIcon = {
                        IconButton(
                            onClick = { onSubmitReply(comment.id) },
                            enabled = commentWithUser.replyText.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Envoyer",
                                tint = if (commentWithUser.replyText.isNotEmpty()) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                }
                            )
                        }
                    },
                    shape = AppSpecificShapes.TextFieldShape
                )
            }
        }

        if (commentWithUser.repliesWithUsers.isNotEmpty()) {
            commentWithUser.repliesWithUsers.forEach { replyWithUser ->
                ReplyItem(
                    replyWithUser = replyWithUser,
                    onUserClick = onUserClick,
                    onLikeReply = onLikeComment
                )
            }
        }
    }
}