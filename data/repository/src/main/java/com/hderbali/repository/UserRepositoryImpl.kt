package com.hderbali.repository

import com.hderbali.model.ResultOf
import com.hderbali.model.User
import com.hderbali.source_local.db.dao.UserDao
import com.hderbali.source_local.db.entities.EntityMappers
import com.hderbali.source_local.network.LoopApiService
import com.hderbali.usecase.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: LoopApiService,
    private val userDao: UserDao,
    private val mappers: EntityMappers
) : UserRepository {

    override fun getUsers(): Flow<ResultOf<List<User>>> = flow {
        emit(ResultOf.Loading)

        // First emit data from local database
        emitAll(userDao.observeAllUsers()
            .map { entities ->
                if (entities.isEmpty()) {
                    ResultOf.Error(IOException("No users found"))
                } else {
                    ResultOf.Success(mappers.run { entities.toUserDomain() })
                }
            }
        )

        // Then try to fetch fresh data
        try {
            refreshUsers()
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override fun getUserById(userId: String): Flow<ResultOf<User>> = flow {
        emit(ResultOf.Loading)

        userDao.observeUserById(userId)
            .collect { entity ->
                if (entity == null) {
                    emit(ResultOf.Error(IOException("User not found")))
                } else {
                    emit(ResultOf.Success(mappers.run { entity.toDomain() }))
                }
            }
    }

    override fun getCurrentUser(): Flow<ResultOf<User>> = flow {
        emit(ResultOf.Loading)

        userDao.observeCurrentUser()
            .collect { entity ->
                if (entity == null) {
                    emit(ResultOf.Error(IOException("Current user not found")))
                } else {
                    emit(ResultOf.Success(mappers.run { entity.toDomain() }))
                }
            }
    }

    override suspend fun refreshUsers() {
        try {
            val remoteUsers = apiService.getUsers()
            val entities = mappers.run { remoteUsers.toUserEntities() }
            userDao.insertUsers(entities)
        } catch (e: Exception) {
            throw e
        }
    }
}
