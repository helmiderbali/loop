package com.hderbali.usecase.usecases.notification.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.notification.MarkAllNotificationsAsReadUseCase
import com.hderbali.usecase.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarkAllNotificationsAsReadUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : MarkAllNotificationsAsReadUseCase {
    override suspend fun invoke(): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)
        try {
            notificationRepository.markAllNotificationsAsRead()
            emit(ResultOf.Success(true))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }
}
