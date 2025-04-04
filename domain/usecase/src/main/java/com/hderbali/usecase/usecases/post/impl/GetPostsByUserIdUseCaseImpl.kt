package com.hderbali.usecase.usecases.post.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.ui.usescases.post.GetPostsByUserIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsByUserIdUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostsByUserIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<List<Post>>> = postRepository.getPostsByUserId(params)
}
