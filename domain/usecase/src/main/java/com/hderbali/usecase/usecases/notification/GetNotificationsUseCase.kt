package com.hderbali.usecase.usecases.notification

import com.hderbali.model.Notification
import com.hderbali.usecase.usecases.NoParamsFlowUseCase

interface GetNotificationsUseCase : NoParamsFlowUseCase<List<Notification>>
