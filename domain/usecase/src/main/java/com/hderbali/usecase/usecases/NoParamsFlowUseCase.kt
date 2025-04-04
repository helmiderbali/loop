package com.hderbali.usecase.usecases

import com.hderbali.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface NoParamsFlowUseCase<R> {
    operator fun invoke(): Flow<ResultOf<R>>
}

