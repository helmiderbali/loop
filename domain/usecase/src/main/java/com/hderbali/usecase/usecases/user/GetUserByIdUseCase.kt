package com.hderbali.usecase.usecases.user

import com.hderbali.model.User
import com.hderbali.usecase.usecases.FlowUseCase

interface GetUserByIdUseCase : FlowUseCase<String, User>
