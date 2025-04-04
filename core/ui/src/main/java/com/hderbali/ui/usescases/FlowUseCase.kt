package com.hderbali.ui.usescases

import com.hderbali.common.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in P, R> {
    operator fun invoke(params: P): Flow<ResultOf<R>>
}
