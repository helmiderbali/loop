package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.notification.GetUnreadNotificationsCountUseCase
import com.hderbali.usecase.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnreadNotificationsCountUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : GetUnreadNotificationsCountUseCase {
    override fun invoke(): Flow<ResultOf<Int>> = notificationRepository.getUnreadNotificationsCount()
}
