package com.hderbali.ui.usescases.notification

import com.hderbali.common.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface MarkAllNotificationsAsReadUseCase {
    suspend operator fun invoke(): Flow<ResultOf<Boolean>>
}
