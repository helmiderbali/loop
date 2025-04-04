package com.hderbali.usecase.usecases

interface SuspendUseCase<in P> {
    suspend operator fun invoke(params: P)
}
