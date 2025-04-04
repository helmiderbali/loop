package com.hderbali.usecase.usecases.post.impl

import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.usecase.usecases.post.LikePostUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikePostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : LikePostUseCase {
    override suspend fun invoke(params: String): Flow<ResultOf<Boolean>> = postRepository.likePost(params)
}

