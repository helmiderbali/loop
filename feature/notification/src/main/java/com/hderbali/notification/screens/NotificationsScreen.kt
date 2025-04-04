package com.hderbali.notification.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hderbali.notification.NotificationsViewModel
import com.hderbali.notification.views.EmptyNotifications
import com.hderbali.notification.views.NotificationsList
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onNavigateToPostDetail: (String) -> Unit,
    onNavigateToUserProfile: (String) -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                actions = {
                    if (state.unreadCount > 0) {
                        FilledIconButton(
                            onClick = { viewModel.markAllAsRead() },
                            modifier = Modifier.padding(end = AppDimensions.spacing8)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = "Marquer tout comme lu"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::refreshNotifications,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading && state.notifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.notifications.isEmpty() && !state.isLoading) {
                EmptyNotifications()
            } else {
                NotificationsList(
                    notifications = state.notifications,
                    onNotificationClick = { notification ->
                        handleNotificationClick(
                            notification = notification.notification,
                            onNavigateToPostDetail = onNavigateToPostDetail,
                            onNavigateToUserProfile = onNavigateToUserProfile
                        )
                        if (!notification.notification.isRead) {
                            viewModel.markAsRead(notification.notification.id)
                        }
                    }
                )
            }
        }
    }
}



@Composable
fun getNotificationTypeColor(type: String): Color {
    return when (type) {
        "like", "like_comment" -> ExtendedTheme.likeColor
        "comment", "comment_reply", "message" -> ExtendedTheme.commentColor
        "follow", "mention" -> MaterialTheme.colorScheme.primary
        "trending" -> Color(0xFFFFC107) // Amber
        "bookmark" -> ExtendedTheme.bookmarkColor
        else -> MaterialTheme.colorScheme.primary
    }
}


