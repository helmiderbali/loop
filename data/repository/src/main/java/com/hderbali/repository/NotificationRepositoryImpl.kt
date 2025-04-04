package com.hderbali.repository

import com.hderbali.model.Notification
import com.hderbali.model.ResultOf
import com.hderbali.source_local.db.dao.NotificationDao
import com.hderbali.source_local.db.entities.EntityMappers
import com.hderbali.source_local.network.LoopApiService
import com.hderbali.usecase.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val apiService: LoopApiService,
    private val notificationDao: NotificationDao,
    private val mappers: EntityMappers
) : NotificationRepository {

    override fun getNotifications(): Flow<ResultOf<List<Notification>>> = flow {
        emit(ResultOf.Loading)

        emitAll(notificationDao.observeAllNotifications()
            .map { entities ->
                if (entities.isEmpty()) {
                    ResultOf.Loading
                } else {
                    ResultOf.Success(mappers.run { entities.toNotificationDomain() })
                }
            }
        )

        try {
            refreshNotifications()
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override fun getUnreadNotificationsCount(): Flow<ResultOf<Int>> = flow {
        emit(ResultOf.Loading)

        emitAll(notificationDao.observeUnreadNotificationsCount()
            .map { count ->
                ResultOf.Success(count)
            }
        )
    }

    override suspend fun markNotificationAsRead(notificationId: String): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)

        try {
            notificationDao.markNotificationAsRead(notificationId)
            emit(ResultOf.Success(true))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun markAllNotificationsAsRead(): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)

        try {
            notificationDao.markAllNotificationsAsRead()
            emit(ResultOf.Success(true))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun refreshNotifications() {
        try {
            val remoteNotifications = apiService.getNotifications()
            val entities = mappers.run { remoteNotifications.toNotificationEntities() }
            notificationDao.insertNotifications(entities)
        } catch (e: Exception) {
            throw e
        }
    }
}
