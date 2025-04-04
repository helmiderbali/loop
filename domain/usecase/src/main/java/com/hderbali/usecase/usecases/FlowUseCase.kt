package com.hderbali.usecase.usecases

import com.hderbali.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in P, R> {
    operator fun invoke(params: P): Flow<ResultOf<R>>
}
