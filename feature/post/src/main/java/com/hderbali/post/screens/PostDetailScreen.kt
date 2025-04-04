// feature-post module

package com.hderbali.post.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.hderbali.post.PostDetailState
import com.hderbali.post.PostDetailViewModel
import com.hderbali.post.views.CommentItem
import com.hderbali.post.views.CommentSection
import com.hderbali.post.views.NewCommentInput
import com.hderbali.post.views.PostActions
import com.hderbali.post.views.PostContent
import com.hderbali.post.views.PostHeader
import com.hderbali.post.views.PostStats
import com.hderbali.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUserProfile: (String) -> Unit,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
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
            } else if (state.post != null) {
                PostDetailContent(
                    state = state,
                    onUserClick = { userId -> onNavigateToUserProfile(userId) },
                    onLikeClick = viewModel::likePost,
                    onBookmarkClick = viewModel::bookmarkPost,
                    onCommentClick = viewModel::toggleCommentSection,
                    onShareClick = { /* TODO: Implémentation du partage */ },
                    onCommentTextChanged = viewModel::updateNewCommentText,
                    onSubmitComment = {
                        viewModel.submitComment()
                        keyboardController?.hide()
                    },
                    onLikeComment = viewModel::likeComment,
                    onReplyModeToggle = viewModel::toggleReplyMode,
                    onReplyTextChanged = viewModel::updateReplyText,
                    onSubmitReply = { commentId ->
                        viewModel.submitReply(commentId)
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}

@Composable
private fun PostDetailContent(
    state: PostDetailState,
    onUserClick: (String) -> Unit,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onCommentTextChanged: (String) -> Unit,
    onSubmitComment: () -> Unit,
    onLikeComment: (String) -> Unit,
    onReplyModeToggle: (String) -> Unit,
    onReplyTextChanged: (String, String) -> Unit,
    onSubmitReply: (String) -> Unit
) {
    val post = state.post ?: return
    val user = state.postOwner

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = AppDimensions.spacing16)
    ) {
        item {
            PostHeader(
                user = user,
                timestamp = post.timestamp,
                location = post.location,
                onUserClick = { user?.id?.let { onUserClick(it) } }
            )
        }

        item {
            PostContent(post = post)
        }

        item {
            PostActions(
                post = post,
                onLikeClick = onLikeClick,
                onCommentClick = onCommentClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
        }

        item {
            PostStats(post = post)
        }

        item {
            CommentSection(
                isExpanded = state.isCommentSectionExpanded,
                commentCount = post.commentCount,
                onToggleExpand = onCommentClick
            )
        }

        if (state.isCommentSectionExpanded) {
            item {
                NewCommentInput(
                    commentText = state.newCommentText,
                    isSubmitting = state.isSubmittingComment,
                    onCommentTextChanged = onCommentTextChanged,
                    onSubmitComment = onSubmitComment
                )
            }

            items(state.comments) { commentWithUser ->
                CommentItem(
                    commentWithUser = commentWithUser,
                    onUserClick = onUserClick,
                    onLikeComment = onLikeComment,
                    onReplyClick = onReplyModeToggle,
                    onReplyTextChanged = onReplyTextChanged,
                    onSubmitReply = onSubmitReply
                )
            }
        }
    }
}

