package com.hderbali.usecase.usecases.post.impl

import com.hderbali.model.Post
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.usecase.usecases.post.GetPostsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostsUseCase {
    override fun invoke(): Flow<ResultOf<List<Post>>> = postRepository.getPosts()
}
