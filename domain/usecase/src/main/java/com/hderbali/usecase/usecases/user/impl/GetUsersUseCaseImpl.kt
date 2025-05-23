package com.hderbali.usecase.usecases.user.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.User
import com.hderbali.ui.usescases.profile.GetUsersUseCase
import com.hderbali.usecase.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUsersUseCase {
    override fun invoke(): Flow<ResultOf<List<User>>> = userRepository.getUsers()
}
