package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Notification
import com.hderbali.ui.usescases.notification.GetNotificationsUseCase
import com.hderbali.usecase.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : GetNotificationsUseCase {
    override fun invoke(): Flow<ResultOf<List<Notification>>> = notificationRepository.getNotifications()
}

