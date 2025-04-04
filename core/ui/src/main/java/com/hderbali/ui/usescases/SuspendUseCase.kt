package com.hderbali.ui.usescases

interface SuspendUseCase<in P> {
    suspend operator fun invoke(params: P)
}
