package com.hderbali.usecase.usecases

import com.hderbali.model.ResultOf
import kotlinx.coroutines.flow.Flow

// Base interface for suspending use cases that return Flow<Result<T>>
interface SuspendFlowUseCase<in P, R> {
    suspend operator fun invoke(params: P): Flow<ResultOf<R>>
}

