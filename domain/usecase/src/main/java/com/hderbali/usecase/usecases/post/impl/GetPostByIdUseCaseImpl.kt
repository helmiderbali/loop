package com.hderbali.usecase.usecases.post.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.ui.usescases.post.GetPostByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostByIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostByIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<Post>> = postRepository.getPostById(params)
}
