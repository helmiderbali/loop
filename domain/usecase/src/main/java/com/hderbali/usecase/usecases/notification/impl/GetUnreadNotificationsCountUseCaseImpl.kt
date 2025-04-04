package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.NotificationRepository
import com.hderbali.usecase.usecases.notification.GetUnreadNotificationsCountUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnreadNotificationsCountUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : GetUnreadNotificationsCountUseCase {
    override fun invoke(): Flow<ResultOf<Int>> = notificationRepository.getUnreadNotificationsCount()
}
