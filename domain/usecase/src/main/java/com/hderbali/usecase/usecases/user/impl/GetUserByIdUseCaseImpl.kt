package com.hderbali.usecase.usecases.user.impl

import com.hderbali.model.ResultOf
import com.hderbali.model.User
import com.hderbali.usecase.repository.UserRepository
import com.hderbali.usecase.usecases.user.GetUserByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserByIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<User>> = userRepository.getUserById(params)
}
