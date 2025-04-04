package com.hderbali.usecase.usecases.user.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.profile.GetCurrentUserUseCase
import com.hderbali.usecase.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetCurrentUserUseCase {
    override fun invoke(): Flow<ResultOf<User>> = userRepository.getCurrentUser()
}
