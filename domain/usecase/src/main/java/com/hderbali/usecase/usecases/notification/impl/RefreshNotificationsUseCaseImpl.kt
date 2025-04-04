package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.usecase.repository.NotificationRepository
import com.hderbali.usecase.usecases.notification.RefreshNotificationsUseCase
import javax.inject.Inject

class RefreshNotificationsUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : RefreshNotificationsUseCase {
    override suspend fun invoke() = notificationRepository.refreshNotifications()
}
