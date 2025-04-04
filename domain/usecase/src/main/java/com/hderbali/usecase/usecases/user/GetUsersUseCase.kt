package com.hderbali.usecase.usecases.user

import com.hderbali.model.User
import com.hderbali.usecase.usecases.NoParamsFlowUseCase

interface GetUsersUseCase : NoParamsFlowUseCase<List<User>>
