package com.hderbali.ui.usescases

import com.hderbali.common.model.ResultOf
import kotlinx.coroutines.flow.Flow

interface NoParamsFlowUseCase<R> {
    operator fun invoke(): Flow<ResultOf<R>>
}

