package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.usecase.repository.NotificationRepository
import com.hderbali.usecase.usecases.notification.MarkAllNotificationsAsReadUseCase
import javax.inject.Inject

class MarkAllNotificationsAsReadUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : MarkAllNotificationsAsReadUseCase {
    override suspend fun invoke(params: Boolean) {
        notificationRepository.markAllNotificationsAsRead()
    }

}

