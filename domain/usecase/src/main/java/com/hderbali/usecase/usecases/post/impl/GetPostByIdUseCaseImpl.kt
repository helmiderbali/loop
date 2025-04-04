package com.hderbali.usecase.usecases.post.impl

import com.hderbali.model.Post
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.usecase.usecases.post.GetPostByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostByIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostByIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<Post>> = postRepository.getPostById(params)
}
