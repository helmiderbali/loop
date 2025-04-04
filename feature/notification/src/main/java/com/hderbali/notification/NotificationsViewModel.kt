package com.hderbali.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.notification.GetNotificationsUseCase
import com.hderbali.ui.usescases.notification.GetUnreadNotificationsCountUseCase
import com.hderbali.ui.usescases.notification.MarkAllNotificationsAsReadUseCase
import com.hderbali.ui.usescases.notification.MarkNotificationAsReadUseCase
import com.hderbali.ui.usescases.notification.RefreshNotificationsUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUnreadNotificationsCountUseCase: GetUnreadNotificationsCountUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val markAllNotificationsAsReadUseCase: MarkAllNotificationsAsReadUseCase,
    private val refreshNotificationsUseCase: RefreshNotificationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    private val userCache = mutableMapOf<String, User>()

    init {
        loadNotifications()
        loadUnreadCount()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                getNotificationsUseCase()
                    .catch { e ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = e.message ?: "Erreur lors du chargement des notifications."
                            )
                        }
                    }
                    .collect { result ->
                        when (result) {
                            is ResultOf.Success -> {
                                val notifications = result.data

                                val notificationsWithUsers = notifications.map { notification ->
                                    val actor = notification.actorId?.let { actorId ->
                                        getUserFromCache(actorId)
                                    }
                                    NotificationWithUser(notification, actor)
                                }

                                _state.update {
                                    it.copy(
                                        notifications = notificationsWithUsers,
                                        isLoading = false,
                                        isRefreshing = false
                                    )
                                }
                            }
                            is ResultOf.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isRefreshing = false,
                                        error = result.exception.message ?: "Une erreur s'est produite."
                                    )
                                }
                            }
                            ResultOf.Loading -> {
                            }
                        }
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement des notifications."
                    )
                }
            }
        }
    }

    private fun loadUnreadCount() {
        viewModelScope.launch {
            try {
                getUnreadNotificationsCountUseCase()
                    .catch { e ->
                    }
                    .collectLatest { result ->
                        if (result is ResultOf.Success) {
                            _state.update { it.copy(unreadCount = result.data) }
                        }
                    }
            } catch (e: Exception) {
            }
        }
    }

    fun refreshNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, error = null) }

            try {
                refreshNotificationsUseCase()
                loadNotifications()
                loadUnreadCount()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "Une erreur s'est produite lors du rafraîchissement."
                    )
                }
            }
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            try {
                markNotificationAsReadUseCase(notificationId).collectLatest { result ->
                    if (result is ResultOf.Success && result.data) {
                        _state.update { state ->
                            val updatedNotifications = state.notifications.map { notificationWithUser ->
                                if (notificationWithUser.notification.id == notificationId && !notificationWithUser.notification.isRead) {
                                    val updatedNotification = notificationWithUser.notification.copy(isRead = true)
                                    notificationWithUser.copy(notification = updatedNotification)
                                } else {
                                    notificationWithUser
                                }
                            }

                            state.copy(
                                notifications = updatedNotifications,
                                unreadCount = (state.unreadCount - 1).coerceAtLeast(0)
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Une erreur s'est produite lors du marquage de la notification."
                    )
                }
            }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            try {
                markAllNotificationsAsReadUseCase().collectLatest { result ->
                    if (result is ResultOf.Success && result.data) {
                        _state.update { state ->
                            val updatedNotifications = state.notifications.map { notificationWithUser ->
                                if (!notificationWithUser.notification.isRead) {
                                    val updatedNotification = notificationWithUser.notification.copy(isRead = true)
                                    notificationWithUser.copy(notification = updatedNotification)
                                } else {
                                    notificationWithUser
                                }
                            }

                            state.copy(
                                notifications = updatedNotifications,
                                unreadCount = 0
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Une erreur s'est produite lors du marquage des notifications."
                    )
                }
            }
        }
    }

    private suspend fun getUserFromCache(userId: String): User? {
        userCache[userId]?.let { return it }

        return try {
            val result = getUserByIdUseCase(userId)
                .first { it is ResultOf.Success || it is ResultOf.Error }
            if (result is ResultOf.Success) {
                val user = result.data
                userCache[userId] = user
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
