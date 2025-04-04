package com.hderbali.usecase.repository

import com.hderbali.model.ResultOf
import com.hderbali.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<ResultOf<List<User>>>
    fun getUserById(userId: String): Flow<ResultOf<User>>
    fun getCurrentUser(): Flow<ResultOf<User>>
    suspend fun refreshUsers()
}
