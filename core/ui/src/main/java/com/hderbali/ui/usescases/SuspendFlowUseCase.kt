package com.hderbali.ui.usescases

import com.hderbali.common.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface SuspendFlowUseCase<in P, R> {
    suspend operator fun invoke(params: P): Flow<ResultOf<R>>
}

