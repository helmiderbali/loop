package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.model.Notification
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.NotificationRepository
import com.hderbali.usecase.usecases.notification.GetNotificationsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : GetNotificationsUseCase {
    override fun invoke(): Flow<ResultOf<List<Notification>>> = notificationRepository.getNotifications()
}

