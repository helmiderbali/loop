package com.hderbali.usecase.usecases.post.impl

import com.hderbali.model.Post
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.usecase.usecases.post.GetPostsByUserIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsByUserIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostsByUserIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<List<Post>>> = postRepository.getPostsByUserId(params)
}
