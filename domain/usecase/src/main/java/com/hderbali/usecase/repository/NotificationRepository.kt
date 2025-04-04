package com.hderbali.usecase.repository

import com.hderbali.model.ResultOf
import com.hderbali.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<ResultOf<List<Notification>>>
    fun getUnreadNotificationsCount(): Flow<ResultOf<Int>>
    suspend fun markNotificationAsRead(notificationId: String): Flow<ResultOf<Boolean>>
    suspend fun markAllNotificationsAsRead(): Flow<ResultOf<Boolean>>
    suspend fun refreshNotifications()
}
