package com.hderbali.usecase.usecases.user.impl

import com.hderbali.model.ResultOf
import com.hderbali.model.User
import com.hderbali.usecase.repository.UserRepository
import com.hderbali.usecase.usecases.user.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUsersUseCase {
    override fun invoke(): Flow<ResultOf<List<User>>> = userRepository.getUsers()
}
