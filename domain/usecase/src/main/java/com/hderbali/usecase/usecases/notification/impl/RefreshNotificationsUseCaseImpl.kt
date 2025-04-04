package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.ui.usescases.notification.RefreshNotificationsUseCase
import com.hderbali.usecase.repository.NotificationRepository
import javax.inject.Inject

class RefreshNotificationsUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : RefreshNotificationsUseCase {
    override suspend fun invoke() = notificationRepository.refreshNotifications()
}
