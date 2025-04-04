package com.hderbali.usecase.usecases.user.impl

import com.hderbali.ui.usescases.profile.RefreshUsersUseCase
import com.hderbali.usecase.repository.UserRepository
import javax.inject.Inject

class RefreshUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : RefreshUsersUseCase {
    override suspend fun invoke() = userRepository.refreshUsers()
}
