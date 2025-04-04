package com.hderbali.usecase.usecases.user.impl

import com.hderbali.usecase.repository.UserRepository
import com.hderbali.usecase.usecases.user.RefreshUsersUseCase
import javax.inject.Inject

class RefreshUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : RefreshUsersUseCase {
    override suspend fun invoke() = userRepository.refreshUsers()
}
