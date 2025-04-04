package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.notification.MarkNotificationAsReadUseCase
import com.hderbali.usecase.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkNotificationAsReadUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : MarkNotificationAsReadUseCase {
    override suspend fun invoke(params: String): Flow<ResultOf<Boolean>> =
        notificationRepository.markNotificationAsRead(params)
}
