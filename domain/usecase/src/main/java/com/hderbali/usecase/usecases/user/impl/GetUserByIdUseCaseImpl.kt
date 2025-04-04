package com.hderbali.usecase.usecases.user.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import com.hderbali.usecase.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserByIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<User>> = userRepository.getUserById(params)
}
